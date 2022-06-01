package com.bangkit.manduin.model

data class LabelModel (
    val label: String,
    val placeName: String,
    val id: Int,
    var confidence: Float = 0f
)