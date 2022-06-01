package com.bangkit.manduin.ui.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.bangkit.manduin.R
import com.bangkit.manduin.databinding.ActivityCameraBinding
import com.bangkit.manduin.databinding.ItemSheetLabelCameraBinding
import com.bangkit.manduin.model.LabelModel
import com.bangkit.manduin.utils.Helper.toPercentage
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.viewmodel.CameraViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var bindingBottomSheet: ItemSheetLabelCameraBinding

    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var loadingDialog: AlertDialog
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val cameraViewModel: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponent()
        startCamera()
        hideSystemUI()
        setObserver()
    }

    private fun initComponent() {
        // Initiate Loading Dialog
        val builder = MaterialAlertDialogBuilder(this)
            .setView(R.layout.loading_dialog)
            .setCancelable(false)
        loadingDialog = builder.create()


        // Initiate Bottom Sheet Dialog
        bottomSheetDialog = BottomSheetDialog(
            this@CameraActivity, R.style.BottomSheetDialogTheme
        )
        bindingBottomSheet = ItemSheetLabelCameraBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bindingBottomSheet.root)

        // Set Onclick
        binding.btnBack.setOnClickListener { super.onBackPressed() }

        binding.btnDetect.setOnClickListener {
            loadingDialog.show()
            takePhoto()
        }
    }

    private fun setObserver() {
        cameraViewModel.result.observe(this) { result ->
            processResult(result)
        }
    }

    private fun processResult(result: Result<LabelModel>) {
        when(result) {
            is Result.Loading -> { showLoading(true) }
            is Result.Success -> {
                showDetectedObject(result.data)
                showLoading(false)
                bottomSheetDialog.show()
            }
            is Result.Error -> {
                Toast.makeText(applicationContext, resources.getString(R.string.detect_failed), Toast.LENGTH_LONG).show()
                showLoading(false)
            }
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(img: ImageProxy) {
                    cameraViewModel.detectImage(img, applicationContext)
                    img.close()
                    Log.d(TAG, "Success Capture Photo")
                }
                override fun onError(exception: ImageCaptureException) {
                    Log.d(TAG, "Failed to Capture Photo")
                    Toast.makeText(this@CameraActivity, resources.getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
                }
        })
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

                setPinchToZoomAndTapToFocus(camera)
            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    resources.getString(R.string.camera_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }


        }, ContextCompat.getMainExecutor(this))
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showDetectedObject(data: LabelModel) {
        val label = data.placeName
        val confidence = data.confidence
        val forProgress = String.format("%.1f", confidence * 100f).toFloat().toInt()

        bindingBottomSheet.apply {
            if (confidence > 0.9) {
                layoutAccuracy.visibility = View.VISIBLE
                pgHorizontal.visibility = View.VISIBLE
                btnDetailLandmark.visibility = View.VISIBLE

                tvPercentage.text = confidence.toPercentage()
                tvLabel.text = label

                CoroutineScope(Dispatchers.Default).launch {
                    for (i in 1 until forProgress + 1) {
                        bindingBottomSheet.pgHorizontal.progress = i
                        delay(5)
                    }
                }
            } else {
                layoutAccuracy.visibility = View.GONE
                pgHorizontal.visibility = View.GONE
                btnDetailLandmark.visibility = View.GONE

                tvLabel.text = resources.getString(R.string.no_object)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) loadingDialog.show()
        else loadingDialog.dismiss()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setPinchToZoomAndTapToFocus(camera: Camera) {
        val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val currentZoomRatio = camera.cameraInfo.zoomState.value?.zoomRatio ?: 0F
                val delta = detector.scaleFactor
                camera.cameraControl.setZoomRatio(currentZoomRatio * delta)
                return true
            }
        }
        val scaleGestureDetector = ScaleGestureDetector(applicationContext, listener)

        binding.viewFinder.setOnTouchListener { _, motionEvent: MotionEvent ->
            scaleGestureDetector.onTouchEvent(motionEvent)
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> return@setOnTouchListener true
                MotionEvent.ACTION_UP -> {
                    val factory = binding.viewFinder.meteringPointFactory
                    val point = factory.createPoint(motionEvent.x, motionEvent.y)
                    val action = FocusMeteringAction.Builder(point).build()
                    camera.cameraControl.startFocusAndMetering(action)

                    return@setOnTouchListener true
                }
                else -> return@setOnTouchListener false
            }
        }
    }

    companion object {
        private const val TAG = "CameraActivity"
    }
}