package com.nguyennhatminh614.composeheavilyapp.ui.view.dashboard

import androidx.lifecycle.ViewModel
import com.nguyennhatminh614.composeheavilyapp.data.mock.DashboardMockData
import com.nguyennhatminh614.composeheavilyapp.data.model.TimeFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        // Load initial data (ONE_MONTH)
        loadDataForFilter(TimeFilter.ONE_MONTH)
    }

    fun onTimeFilterChanged(filter: TimeFilter) {
        if (_uiState.value.selectedTimeFilter == filter) return
        loadDataForFilter(filter)
    }

    private fun loadDataForFilter(filter: TimeFilter) {
        val (yValues, xLabels) = DashboardMockData.getChartDataFor(filter)
        _uiState.update { current ->
            current.copy(
                selectedTimeFilter = filter,
                chartDataYValues = yValues,
                chartDataXLabels = xLabels,
                historyLogs = DashboardMockData.recentHistory,
                // Update current weight depending on the last value in the chart
                currentWeight = yValues.lastOrNull() ?: current.currentWeight,
                // Simple mock calculation for difference between first and last of this period
                weightDifference = if (yValues.size >= 2) (yValues.last() - yValues.first()) else 0f
            )
        }
    }
}
