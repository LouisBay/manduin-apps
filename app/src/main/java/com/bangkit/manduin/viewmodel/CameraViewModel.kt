package com.bangkit.manduin.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.manduin.data.repository.MLRepository
import com.bangkit.manduin.ml.Model
import com.bangkit.manduin.model.LabelModel
import com.bangkit.manduin.utils.Helper.imageProxyToBitmap
import com.bangkit.manduin.utils.Helper.rotateBitmap
import com.bangkit.manduin.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val mlRepository: MLRepository
) : ViewModel() {
    private val _result = MutableLiveData<Result<LabelModel>>()
    val result: LiveData<Result<LabelModel>> = _result

    private var labelList = listOf<LabelModel>()

    init {
        viewModelScope.launch {
            mlRepository.getModelLabelsFromFile().collect {
                labelList = it
            }
        }
    }

    fun detectImage(imageProxy: ImageProxy, context: Context) {
        _result.value = Result.Loading
        val image = preprocessImage(imageProxy)

        try {
            val model = Model.newInstance(context)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

            val byteBuffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            val intValues = IntArray(IMAGE_SIZE * IMAGE_SIZE)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

            var pixel = 0

            for (i in 0 until IMAGE_SIZE) {
                for (j in 0 until IMAGE_SIZE) {
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

            labelList[maxPos].apply { confidence = maxConfidence }.also {
                _result.value = Result.Success(it)
            }

            // Releases model resources if no longer used.
            model.close()
        } catch (e: Exception) {
            _result.value = Result.Error(e.localizedMessage?.toString() ?: e.message.toString())
        }
    }

    private fun preprocessImage(imageProxy: ImageProxy): Bitmap {
        var imageBitmap = imageProxyToBitmap(imageProxy)
        imageBitmap = Bitmap.createScaledBitmap(
            imageBitmap,
            IMAGE_SIZE,
            IMAGE_SIZE,
            false
        )
        imageBitmap = rotateBitmap(imageBitmap, imageProxy)
        return imageBitmap
    }


    companion object {
        private const val IMAGE_SIZE = 224
    }
}