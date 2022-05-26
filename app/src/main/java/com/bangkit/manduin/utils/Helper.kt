package com.bangkit.manduin.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
}