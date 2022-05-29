package com.bangkit.manduin.model

data class ModelClasificaitonItem (
    val label: String,
    val confidence: Float
) {
    val presentase = if (confidence == 0F) "" else String.format("%.1f%%", confidence * 100.0f)

    override fun toString(): String {
        return " $label : $presentase"
    }
}