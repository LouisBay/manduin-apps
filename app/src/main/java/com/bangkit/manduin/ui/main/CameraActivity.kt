package com.bangkit.manduin.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.bangkit.manduin.R
import com.bangkit.manduin.databinding.ActivityCameraBinding
import com.bangkit.manduin.utils.Helper.toPercentage
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.viewmodel.CameraViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var loadingDialog: AlertDialog

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
        val builder = MaterialAlertDialogBuilder(this)
            .setView(R.layout.loading_dialog)
            .setCancelable(false)
        loadingDialog = builder.create()

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

    private fun processResult(result: Result<Array<Any>>) {
        when(result) {
            is Result.Loading -> { showLoading(true) }
            is Result.Success -> {
                showDetectedObject(result.data)
                showLoading(false)
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
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
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

    private fun showDetectedObject(data: Array<Any>) {
//        val text = if ((data[1] as Float) > 0.8) {
//            String.format("%s : %s", data[0].toString(), (data[1] as Float).toPercentage())
//        } else "No Object Detected"

        val label = data[0].toString()
        val confidence = data[1] as Float
        val forProgress = String.format("%.1f", confidence * 100f).toFloat().toInt()


        binding.apply {
            layout.cardLabel.visibility = View.VISIBLE
            if (confidence > 0.8) {
                layout.tvPercentage.visibility = View.VISIBLE
                layout.textAccuracy.visibility = View.VISIBLE
                layout.pgHorizontal.visibility = View.VISIBLE
                layout.tvPercentage.text = confidence.toPercentage()
                layout.tvLabel.text = label
                CoroutineScope(Dispatchers.Default).launch {
                    for (i in 1 until forProgress + 1) {
                        layout.pgHorizontal.progress = i
                        delay(10)
                    }
                }
            } else {
                layout.textAccuracy.visibility = View.GONE
                layout.tvPercentage.visibility = View.GONE
                layout.pgHorizontal.visibility = View.GONE
                layout.tvLabel.text = "No Object Detected"
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) loadingDialog.show()
        else loadingDialog.dismiss()
    }

    companion object {
        const val imageSize = 224
        private const val TAG = "CameraActivity"
    }
}