package com.bangkit.manduin.model

data class UserSessionModel(
    val uid: String,
    val name: String,
    val email: String,
    val isLogin: Boolean,
)
