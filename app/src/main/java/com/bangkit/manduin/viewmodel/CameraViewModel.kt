package com.bangkit.manduin.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.manduin.ml.Model
import com.bangkit.manduin.ui.main.CameraActivity
import com.bangkit.manduin.utils.DataDummy
import com.bangkit.manduin.utils.Helper
import com.bangkit.manduin.utils.Result
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CameraViewModel : ViewModel() {
    private val _result = MutableLiveData<Result<Array<Any>>>()
    val result: LiveData<Result<Array<Any>>> = _result

    fun detectImage(imageProxy: ImageProxy, context: Context) {
        _result.value = Result.Loading
        val image = preprocessImage(imageProxy)

        try {
            val model = Model.newInstance(context)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

            val byteBuffer = ByteBuffer.allocateDirect(4 * CameraActivity.imageSize * CameraActivity.imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            val intValues = IntArray(CameraActivity.imageSize * CameraActivity.imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

            var pixel = 0

            for (i in 0 until CameraActivity.imageSize) {
                for (j in 0 until CameraActivity.imageSize) {
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

            _result.value = Result.Success(arrayOf(classes[maxPos], maxConfidence))

            // Releases model resources if no longer used.
            model.close()
        } catch (e: Exception) {
            _result.value = Result.Error(e.localizedMessage?.toString() ?: e.message.toString())
        }

    }

    private fun preprocessImage(image: ImageProxy): Bitmap {
        var imageBitmap = Helper.imageProxyToBitmap(image)
        imageBitmap = Bitmap.createScaledBitmap(
            imageBitmap,
            CameraActivity.imageSize,
            CameraActivity.imageSize,
            false
        )
        imageBitmap = Helper.rotateBitmap(imageBitmap, true)
        return imageBitmap
    }
}