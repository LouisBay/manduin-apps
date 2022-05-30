package com.bangkit.manduin.ui.main

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.bangkit.manduin.databinding.ActivityCameraBinding
import com.bangkit.manduin.ml.Model
import com.bangkit.manduin.utils.DataDummy
import com.bangkit.manduin.utils.Helper
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var image: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivClose.setOnClickListener {
            super.onBackPressed()
        }

        binding.ivCapture.setOnClickListener { take() }

        startCamera()
        hideSystemUI()
    }

    private fun take() {
        val imageCapture = imageCapture ?: return

//        val photoFile = Helper.createFile(application)
//
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(this),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    Intent(this@CameraActivity, PreviewCamResult::class.java).apply {
//                        putExtra("picture", photoFile)
//                        putExtra("isBackCamera", cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
//                    }.also { startActivity(it) }
//                    finish()
//                }
//
//                override fun onError(exception: ImageCaptureException) {
//                    Toast.makeText(this@CameraActivity, "Gagal mengambil gambar.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        )
//
//        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    this@CameraActivity.image = Helper.imageProxyToBitmap(image)
                    setImage()
//                    binding.previewiv.setImageBitmap(this@CameraActivity.image)
                    classifyImage()
                    image.close()
                    Log.d("CAM", "Success")
                }
                override fun onError(exception: ImageCaptureException) {
                    Log.d("CAM", "Error Capture")
                    Toast.makeText(this@CameraActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
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
                    "Gagal memunculkan kamera.",
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

    private fun setImage() {
        image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
        image = Helper.rotateBitmap(
            image,
            true)
    }

    private fun classifyImage() {
        val model = Model.newInstance(applicationContext)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//
        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())


        val intValues = IntArray(imageSize * imageSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

        var pixel = 0

//        for (i in 0 until imageSize) {
//            for (j in 0 until imageSize) {
//                val value = intValues[pixel++]
//                byteBuffer.putFloat(((((value shr 16) and 0xFF) * (1f / 255)) - 1f) * 2f)
//                byteBuffer.putFloat(((((value shr 8) and 0xFF) * (1f / 255)) - 1f) * 2f)
//                byteBuffer.putFloat((((value and 0xFF) * (1f / 255)) - 1f) * 2f)
//            }
//        }

        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val value = intValues[pixel++]
                byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255))
                byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255))
                byteBuffer.putFloat(((value and 0xFF) * (1f / 255)))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        var maxPos = 0
        var maxConfidence = 0f

        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }


        val classes = DataDummy.getModelLabel()

        if (maxConfidence > 0.8) {
            binding.tvLabel.text = "${classes[maxPos]} : $maxConfidence"
        } else {
            binding.tvLabel.text = ""
        }

        // Releases model resources if no longer used.
        model.close()
    }

    companion object {
        private const val imageSize = 224
    }
}