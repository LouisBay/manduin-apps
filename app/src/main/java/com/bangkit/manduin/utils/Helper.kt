package com.bangkit.manduin.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.camera.core.ImageProxy
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.nio.ByteBuffer

object Helper {
    fun String.isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

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

    fun imageProxyToBitmap(image: ImageProxy): Bitmap {
        val planeProxy = image.planes[0]
        val buffer: ByteBuffer = planeProxy.buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}