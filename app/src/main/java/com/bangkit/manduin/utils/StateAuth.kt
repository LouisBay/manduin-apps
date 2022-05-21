package com.bangkit.manduin.utils

sealed class StateAuth private constructor() {
    data class Error(val message: String) : StateAuth()
    object Loading : StateAuth()
    object Success : StateAuth()
}