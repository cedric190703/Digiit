package com.example.digiit.graphs

import androidx.compose.ui.graphics.Color
import com.example.digiit.R
import com.github.mikephil.charting.data.BarEntry

enum class TypeGraph(
    val title: String,
    val icon: Int
) {
    CubicLine(
        title = "Cubic Lines chart",
        icon = R.drawable.line_chart
    ),
    LineChart(
        title = "Cubic Lines chart",
        icon = R.drawable.line_chart
    ),
    BubbleGraph(
        title = "Bubble graph",
        icon = R.drawable.bubblechart
    ),
    BarChart(
        title = "Bar chart",
        icon = R.drawable.barchart
    ),
    GroupedBarChart(
        title = "Grouped chart",
        icon = R.drawable.groupe_graph
    ),
    PieChart(
        title = "Pie chart",
        icon = R.drawable.piechart
    ),
    HorizontalBarChart(
        title = "Horizontal chart",
        icon = R.drawable.horizontal_chart
    ),
    RadarChartData(
        title = "Radar chart",
        icon = R.drawable.radarchart
    )
}

sealed class Graph {
    abstract val typeGraph: TypeGraph
    abstract val title: String
    abstract val colorGraph: Color
}

data class BubbleGraphData(
    override val typeGraph: TypeGraph,
    override val title: String,
    override val colorGraph: Color,
    var data: List<Triple<Float, Float, Float>>
) : Graph()

data class BarChartData(
    override val typeGraph: TypeGraph,
    override val title: String,
    override val colorGraph: Color,
    var data: List<Pair<String, Int>>
) : Graph()

data class LineChartData(
    override val typeGraph: TypeGraph,
    override val title: String,
    override val colorGraph: Color,
    var data: List<Pair<String, Int>>
) : Graph()

data class CubicLineChartData(
    override val typeGraph: TypeGraph,
    override val title: String,
    override val colorGraph: Color,
    var data: List<Pair<String, Int>>
) : Graph()

data class GroupedBarChartData(
    override val typeGraph: TypeGraph,
    override val title: String,
    override val colorGraph: Color,
    var data: List<BarEntry>
) : Graph()

data class PieChartData(
    override val typeGraph: TypeGraph,
    override val title: String,
    override val colorGraph: Color,
    var data: List<Pair<String, Int>>
) : Graph()

data class HorizontalBarChartData(
    override val typeGraph: TypeGraph,
    override val title: String,
    override val colorGraph: Color,
    var data: List<Pair<String, Int>>
) : Graph()

data class BubbleChartData(
    override val typeGraph: TypeGraph,
    override val title: String,
    override val colorGraph: Color,
    var data: List<Pair<String, Int>>
) : Graph()

data class RadarChartData(
    override val typeGraph: TypeGraph,
    override val title: String,
    override val colorGraph: Color,
    var data: List<Pair<String, Int>>
) : Graph()