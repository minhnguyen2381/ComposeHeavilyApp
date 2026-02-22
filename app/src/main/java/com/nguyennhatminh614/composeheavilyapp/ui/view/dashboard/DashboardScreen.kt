package com.nguyennhatminh614.composeheavilyapp.ui.view.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nguyennhatminh614.composeheavilyapp.data.model.TimeFilter
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1B14)) // Nền Dark Green tối
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        DashboardHeader()
        Spacer(modifier = Modifier.height(24.dp))

        TimeFilterTabs(
            selectedFilter = uiState.selectedTimeFilter,
            onFilterSelected = viewModel::onTimeFilterChanged
        )

        Spacer(modifier = Modifier.height(32.dp))

        WeightTrendSection(uiState)
        
        Spacer(modifier = Modifier.height(16.dp))

        WeightChartSection(uiState)

        Spacer(modifier = Modifier.height(24.dp))

        InfoCardsSection(uiState)

        Spacer(modifier = Modifier.height(32.dp))

        RecentHistorySection(uiState)
    }
}

@Composable
fun DashboardHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "Welcome back", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = "Dashboard",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        IconButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .background(Color(0xFF16251A), shape = RoundedCornerShape(12.dp))
        ) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
        }
    }
}

@Composable
fun TimeFilterTabs(selectedFilter: TimeFilter, onFilterSelected: (TimeFilter) -> Unit) {
    val filters = remember { TimeFilter.entries } // Kotlin 1.7-1.8 safe

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF16251A))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        filters.forEach { filter ->
            TimeFilterTabItem(
                filter = filter,
                isSelected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) }
            )
        }
    }
}

@Composable
private fun TimeFilterTabItem(
    filter: TimeFilter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) Color(0xFF00FF66) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = filter.title,
            color = if (isSelected) Color.Black else Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

@Composable
fun WeightTrendSection(uiState: DashboardUiState) {
    Column {
        Text("Weight Trend", color = Color.Gray, fontSize = 14.sp)
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = String.format("%.1f", uiState.currentWeight),
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = " kg",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            
            // Difference Badge
            val diffColor = if (uiState.weightDifference <= 0) Color(0xFF00FF66) else Color(0xFFFF5252)
            val diffPrefix = if (uiState.weightDifference > 0) "+" else ""
            Surface(
                color = diffColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Text(
                    text = "$diffPrefix${String.format("%.1f", uiState.weightDifference)} kg",
                    color = diffColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun WeightChartSection(uiState: DashboardUiState) {
    val entries = uiState.chartDataYValues.mapIndexed { index, y ->
        FloatEntry(x = index.toFloat(), y = y)
    }
    
    // Safety check map list to empty
    if(entries.isEmpty()) return

    val chartEntryModelProducer = remember { ChartEntryModelProducer() }
    chartEntryModelProducer.setEntries(entries)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        backgroundColor = Color(0xFF16251A),
        shape = RoundedCornerShape(16.dp)
    ) {
        val lightGreen = Color(0xFF00FF66)
        val lineChart = lineChart(
            lines = listOf(
                com.patrykandpatrick.vico.compose.chart.line.lineSpec(
                    lineColor = lightGreen,
                    lineBackgroundShader = DynamicShaders.fromBrush(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                lightGreen.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    )
                )
            ),
            decorations = listOf(
                com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine(
                    thresholdValue = uiState.targetWeight,
                    lineComponent = com.patrykandpatrick.vico.core.component.shape.ShapeComponent(
                        shape = Shapes.rectShape,
                        color = android.graphics.Color.argb(128, 255, 255, 255)
                    ),
                    labelComponent = com.patrykandpatrick.vico.core.component.text.TextComponent.Builder().apply {
                         color = android.graphics.Color.WHITE
                         background = com.patrykandpatrick.vico.core.component.shape.ShapeComponent(shape = Shapes.pillShape, color = android.graphics.Color.parseColor("#00FF66"))
                         margins = com.patrykandpatrick.vico.core.dimensions.MutableDimensions(4f,4f,4f,4f)
                         padding = com.patrykandpatrick.vico.core.dimensions.MutableDimensions(8f,8f,8f,8f)
                    }.build(),
                    labelHorizontalPosition = com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine.LabelHorizontalPosition.Start
                )
            )
        )

        Chart(
            chart = lineChart,
            chartModelProducer = chartEntryModelProducer,
            startAxis = rememberStartAxis(
                label = textComponent(color = Color.Gray),
                guideline = null
            ),
            bottomAxis = rememberBottomAxis(
                label = textComponent(color = Color.Gray),
                guideline = null,
                valueFormatter = { value, _ ->
                    uiState.chartDataXLabels.getOrNull(value.toInt()) ?: ""
                }
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun InfoCardsSection(uiState: DashboardUiState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // BMI Card
        Card(
            modifier = Modifier.weight(1f),
            backgroundColor = Color(0xFF16251A),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("BMI", color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.bmi.toString(),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("Normal Weight", color = Color.Gray, fontSize = 12.sp)
            }
        }

        // Target Card
        Card(
            modifier = Modifier.weight(1f),
            backgroundColor = Color(0xFF16251A),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("TARGET", color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = uiState.targetWeight.toString(),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(" kg", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = 0.7f,
                    color = Color(0xFFB388FF),
                    backgroundColor = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp))
                )
                Text(
                    text = "${String.format("%.1f", uiState.currentWeight - uiState.targetWeight)} kg left",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 4.dp).align(Alignment.End)
                )
            }
        }
    }
}

@Composable
fun RecentHistorySection(uiState: DashboardUiState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Recent History", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("View All", color = Color(0xFF00FF66), fontSize = 14.sp, modifier = Modifier.clickable { })
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        uiState.historyLogs.forEach { log ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color(0xFF16251A), RoundedCornerShape(12.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("${log.weightKg} kg", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(log.dateLabel, color = Color.Gray, fontSize = 12.sp)
                }
                
                val diffColor = if (log.weightDifference <= 0) Color(0xFF00FF66) else Color(0xFFFF5252)
                val diffPrefix = if (log.weightDifference > 0) "+" else ""
                Text(
                    text = "$diffPrefix${log.weightDifference} kg",
                    color = diffColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
