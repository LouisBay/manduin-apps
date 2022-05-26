package com.bangkit.manduin.model

data class UserSessionModel(
    val uid: String = "",
    val fullname: String = "",
    val email: String = "",
    val isLogin: Boolean = false,
)
