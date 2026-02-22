package com.nguyennhatminh614.composeheavilyapp.data.mock

import com.nguyennhatminh614.composeheavilyapp.data.model.TimeFilter
import com.nguyennhatminh614.composeheavilyapp.data.model.WeightLog
import java.util.UUID

object DashboardMockData {
    // Fake History Records
    val recentHistory = listOf(
        WeightLog(UUID.randomUUID().toString(), "Aug 29", 72.5f, -0.4f),
        WeightLog(UUID.randomUUID().toString(), "Aug 22", 72.9f, -0.2f),
        WeightLog(UUID.randomUUID().toString(), "Aug 15", 73.1f, -0.5f)
    )

    // Helper: Fake Data for Charts mapping TimeFilter to List of Y values (Weight)
    // The X values will naturally be their index 0, 1, 2...
    fun getChartDataFor(filter: TimeFilter): Pair<List<Float>, List<String>> {
        return when (filter) {
            TimeFilter.ONE_WEEK -> listOf(73.5f, 73.1f, 72.9f, 72.5f) to listOf("Mon", "Wed", "Fri", "Sun")
            TimeFilter.ONE_MONTH -> listOf(74.0f, 73.5f, 73.1f, 72.5f) to listOf("Aug 1", "Aug 8", "Aug 15", "Aug 29")
            TimeFilter.THREE_MONTHS -> listOf(75.5f, 74.5f, 73.5f, 72.5f) to listOf("Jun", "Jul", "Aug", "Sep")
            TimeFilter.ONE_YEAR -> listOf(80f, 78f, 76f, 74f, 72.5f) to listOf("Jan", "Apr", "Jul", "Oct", "Dec")
            TimeFilter.ALL -> listOf(85f, 80f, 75f, 72.5f) to listOf("2021", "2022", "2023", "2024")
        }
    }
}
