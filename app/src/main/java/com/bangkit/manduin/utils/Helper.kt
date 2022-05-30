package com.bangkit.manduin.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.camera.core.ImageProxy
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.nio.ByteBuffer

object Helper {

    // Float to Percentage
    fun Float.toPercentage() = String.format("%.1f%%", this * 100f)

    // Email Pattern Validation
    fun String.isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

    // Error Input Listener TextInputEditTExt
    fun TextInputEditText.addTextErrorListener(textInputLayout: TextInputLayout) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textInputLayout.isErrorEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        this.addTextChangedListener(textWatcher)
    }

    // Convert Image Proxy to Bitmap
    fun imageProxyToBitmap(image: ImageProxy): Bitmap {
        val planeProxy = image.planes[0]
        val buffer: ByteBuffer = planeProxy.buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    // Rotate Image Bitmap to Normal for CameraX
    fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
        val matrix = Matrix()
        return if (isBackCamera) {
            matrix.postRotate(90f)
            Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )
        } else {
            matrix.postRotate(-90f)
            matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
            Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )
        }
    }
}