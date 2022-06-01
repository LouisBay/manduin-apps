package com.bangkit.manduin.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageProxy
import com.bangkit.manduin.ml.Model
import com.bangkit.manduin.model.LabelModel
import com.bangkit.manduin.utils.Helper
import com.bangkit.manduin.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.inject.Inject

class MLRepository @Inject constructor(
    private val context: Context
) {
    fun getItemDetected(imageProxy: ImageProxy) = flow {
        emit(Result.Loading)
        val image = preprocessImage(imageProxy)

        try {
            val model = Model.newInstance(context)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

            var pixel = 0

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

            getModelLabelsFromFile().collect {
                val detectedModel = it[maxPos]
                detectedModel.confidence = maxConfidence
                emit(Result.Success(detectedModel))
            }

            // Releases model resources if no longer used.
            model.close()
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: e.message.toString()))
        }
    }

    private fun getModelLabelsFromFile() = flow {
        val reader = BufferedReader(InputStreamReader(context.assets.open("labels.txt")))
        val labelList = mutableListOf<LabelModel>()
        var label: List<String>

        reader.lineSequence().forEach { result ->
            label = result.split("|")
            if (label.isNotEmpty()) {
                labelList.add(LabelModel(label[0], label[1], label[2].toInt()))
            }
        }
        reader.close()
        emit(labelList)
        Log.d("TEST", labelList.toString())
    }.flowOn(Dispatchers.IO)

    private fun preprocessImage(imageProxy: ImageProxy): Bitmap {
        var imageBitmap = Helper.imageProxyToBitmap(imageProxy)
        imageBitmap = Bitmap.createScaledBitmap(
            imageBitmap,
            imageSize,
            imageSize,
            false
        )
        imageBitmap = Helper.rotateBitmap(imageBitmap, imageProxy)
        return imageBitmap
    }

    companion object {
        private const val imageSize = 224
    }
}