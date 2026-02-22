package com.nguyennhatminh614.composeheavilyapp.data.model

data class WeightLog(
    val id: String,
    val dateLabel: String,
    val weightKg: Float,
    val weightDifference: Float = 0f
)
