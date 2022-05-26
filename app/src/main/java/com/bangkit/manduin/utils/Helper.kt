package com.bangkit.manduin.utils

import android.util.Patterns

object Helper {
    fun String.isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(this).matches()
}