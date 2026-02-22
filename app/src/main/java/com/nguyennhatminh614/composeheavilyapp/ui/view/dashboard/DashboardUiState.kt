package com.nguyennhatminh614.composeheavilyapp.ui.view.dashboard

import com.nguyennhatminh614.composeheavilyapp.data.model.TimeFilter
import com.nguyennhatminh614.composeheavilyapp.data.model.WeightLog

data class DashboardUiState(
    val selectedTimeFilter: TimeFilter = TimeFilter.ONE_MONTH,
    val currentWeight: Float = 72.5f,
    val weightDifference: Float = -1.2f, // Giảm 1.2 kg
    val targetWeight: Float = 68.0f,
    val bmi: Float = 22.4f,
    val chartDataYValues: List<Float> = emptyList(),
    val chartDataXLabels: List<String> = emptyList(),
    val historyLogs: List<WeightLog> = emptyList()
)
