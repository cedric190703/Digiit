package com.example.digiit.graphs

import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.formatter.ValueFormatter
import androidx.compose.material.Card
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.charts.HorizontalBarChart

@Composable
fun BarChart() {
    val dataPoints = listOf(
        Pair("January", 25f),
        Pair("February", 30f),
        Pair("March", 15f),
        Pair("April", 50f),
        Pair("May", 40f),
        Pair("June", 20f)
    )

    val barEntries = dataPoints.mapIndexed { index, data ->
        BarEntry(index.toFloat(), data.second)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                BarChart(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setDrawBarShadow(false)
                    setDrawValueAboveBar(true)
                    description.isEnabled = false
                    setMaxVisibleValueCount(60)
                    setPinchZoom(false)
                    setDrawGridBackground(false)

                    val barDataSet = BarDataSet(barEntries, "")
                    barDataSet.colors = listOf(Color.GREEN)

                    val barData = BarData(barDataSet)
                    data = barData

                    axisLeft.isEnabled = false
                    axisRight.isEnabled = false
                    xAxis.apply {
                        position = XAxisPosition.BOTTOM
                        granularity = 1f
                        setDrawGridLines(false)
                        valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return dataPoints[value.toInt()].first
                            }
                        }
                    }
                    legend.isEnabled = false
                }
            },
            update = { chart ->
                val barDataSet = BarDataSet(barEntries, "")
                barDataSet.colors = listOf(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED)

                val barData = BarData(barDataSet)
                chart.data = barData
                chart.notifyDataSetChanged()
            }
        )
    }
}

@Composable
fun LineChart() {
    val dataPoints = listOf(
        Pair("January", 25f),
        Pair("February", 30f),
        Pair("March", 15f),
        Pair("April", 50f),
        Pair("May", 40f),
        Pair("June", 20f)
    )

    val lineEntries = dataPoints.mapIndexed { index, data ->
        Entry(index.toFloat(), data.second)
    }
    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = androidx.compose.ui.graphics.Color.Blue,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(modifier = Modifier.padding(8.dp),
                factory = { context ->
                    LineChart(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setDrawGridBackground(false)
                        description.isEnabled = false

                        val lineDataSet = LineDataSet(lineEntries, "")
                        lineDataSet.color = Color.BLUE
                        lineDataSet.lineWidth = 2f
                        lineDataSet.circleRadius = 5f
                        lineDataSet.setCircleColor(Color.BLUE)
                        lineDataSet.valueTextColor = Color.BLACK
                        lineDataSet.valueTextSize = 13f
                        lineDataSet.valueTypeface = Typeface.DEFAULT_BOLD

                        val lineData = LineData(lineDataSet)
                        data = lineData

                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false
                        xAxis.apply {
                            position = XAxisPosition.BOTTOM
                            granularity = 1f
                            setDrawGridLines(false)
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return dataPoints[value.toInt()].first
                                }
                            }
                            textSize = 12f
                            typeface = Typeface.DEFAULT_BOLD
                        }
                        legend.isEnabled = false
                    }
                },
                update = { chart ->
                    val lineDataSet = LineDataSet(lineEntries, "")
                    lineDataSet.color = Color.BLUE
                    lineDataSet.lineWidth = 2f
                    lineDataSet.circleRadius = 5f
                    lineDataSet.setCircleColor(Color.BLUE)
                    lineDataSet.valueTextColor = Color.BLACK
                    lineDataSet.valueTextSize = 14f
                    lineDataSet.valueTypeface = Typeface.DEFAULT_BOLD

                    val lineData = LineData(lineDataSet)
                    chart.data = lineData
                    chart.notifyDataSetChanged()
                }
            )
        }
    }
}

@Composable
fun CubicLineChart() {
    val dataPoints = listOf(
        Pair("January", 25f),
        Pair("February", 30f),
        Pair("March", 15f),
        Pair("April", 50f),
        Pair("May", 40f),
        Pair("June", 20f)
    )

    val lineEntries = dataPoints.mapIndexed { index, data ->
        Entry(index.toFloat(), data.second)
    }
    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = androidx.compose.ui.graphics.Color.Blue,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(modifier = Modifier.padding(8.dp),
                factory = { context ->
                    val chart = LineChart(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setDrawGridBackground(false)
                        description.isEnabled = false

                        // Set cubic line chart properties
                        val dataSet = LineDataSet(lineEntries, "")
                        dataSet.color = Color.BLUE
                        dataSet.lineWidth = 4f
                        dataSet.circleRadius = 5f
                        dataSet.setCircleColor(Color.BLUE)
                        dataSet.valueTextColor = Color.BLACK
                        dataSet.valueTextSize = 13f
                        dataSet.valueTypeface = Typeface.DEFAULT_BOLD
                        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                        dataSet.cubicIntensity = 0.2f

                        // Set the fill color to blue
                        dataSet.setDrawFilled(true)
                        dataSet.fillColor = Color.BLUE

                        // Set data and axis properties
                        val lineData = LineData(dataSet)
                        data = lineData

                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            granularity = 1f
                            setDrawGridLines(false)
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return dataPoints[value.toInt()].first
                                }
                            }
                            textSize = 12f
                            typeface = Typeface.DEFAULT_BOLD
                        }
                        legend.isEnabled = false
                    }
                    chart
                },
                update = { chart ->
                    val dataSet = chart.data.getDataSetByIndex(0) as LineDataSet
                    dataSet.values = lineEntries
                    chart.data.notifyDataChanged()
                    chart.notifyDataSetChanged()
                }
            )
        }
    }
}

@Composable
fun GroupedBarChart() {
    val dataSet1 = BarDataSet(
        listOf(
            BarEntry(1f, floatArrayOf(20f, 10f)),
            BarEntry(2f, floatArrayOf(30f, 15f)),
            BarEntry(3f, floatArrayOf(15f, 5f)),
            BarEntry(4f, floatArrayOf(25f, 20f)),
            BarEntry(5f, floatArrayOf(35f, 25f)),
            BarEntry(6f, floatArrayOf(18f, 8f)),
        ),
        "Group 1"
    )
    dataSet1.stackLabels = arrayOf("Value 1", "Value 2")
    dataSet1.color = Color.BLUE
    dataSet1.setDrawValues(false)

    val dataSet2 = BarDataSet(
        listOf(
            BarEntry(1f, floatArrayOf(15f, 25f)),
            BarEntry(2f, floatArrayOf(20f, 30f)),
            BarEntry(3f, floatArrayOf(10f, 15f)),
            BarEntry(4f, floatArrayOf(30f, 25f)),
            BarEntry(5f, floatArrayOf(25f, 35f)),
            BarEntry(6f, floatArrayOf(10f, 18f)),
        ),
        "Group 2",

    )
    dataSet2.stackLabels = arrayOf("Value 1", "Value 2")
    dataSet2.color = Color.RED
    dataSet2.setDrawValues(false)

    val dataSet3 = BarDataSet(
        listOf(
            BarEntry(1f, floatArrayOf(15f, 25f)),
            BarEntry(2f, floatArrayOf(20f, 30f)),
            BarEntry(3f, floatArrayOf(10f, 15f)),
            BarEntry(4f, floatArrayOf(30f, 25f)),
            BarEntry(5f, floatArrayOf(25f, 35f)),
            BarEntry(6f, floatArrayOf(10f, 18f)),
        ),
        "Group 2"
    )
    dataSet3.stackLabels = arrayOf("Value 1", "Value 2")
    dataSet3.color = Color.GREEN
    dataSet3.setDrawValues(false)

    val barData = BarData(dataSet1, dataSet2, dataSet3)

    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = androidx.compose.ui.graphics.Color.Blue,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.padding(8.dp),
                factory = { context ->
                    BarChart(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setDrawGridBackground(false)
                        description.isEnabled = false

                        setDrawBarShadow(false)
                        setDrawValueAboveBar(false)
                        setPinchZoom(false)
                        setScaleEnabled(false)
                        isDoubleTapToZoomEnabled = false
                        setTouchEnabled(false)

                        legend.isEnabled = false

                        xAxis.apply {
                            granularity = 1f
                            setDrawGridLines(false)
                            setDrawAxisLine(false)
                        }

                        axisLeft.apply {
                            axisMinimum = 0f
                            setDrawGridLines(false)
                            setDrawAxisLine(false)
                        }

                        axisRight.apply {
                            axisMinimum = 0f
                            setDrawGridLines(false)
                            setDrawAxisLine(false)
                        }

                        data = barData
                        groupBars(0f, 0.22f, 0.02f)
                        invalidate()
                    }
                }
            )
        }
    }
}

@Composable
fun PieChart() {
    val dataPoints = listOf(
        Pair("January", 25f),
        Pair("February", 30f),
        Pair("March", 15f),
        Pair("April", 50f),
        Pair("May", 40f),
        Pair("June", 20f)
    )

    val pieEntries = dataPoints.mapIndexed { _, data ->
        PieEntry(data.second, data.first)
    }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = androidx.compose.ui.graphics.Color.Blue,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { context ->
                    PieChart(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        description.isEnabled = false
                        setUsePercentValues(true)
                        setDrawEntryLabels(false)
                        setDrawHoleEnabled(false)

                        // Customize the text paint object
                        setEntryLabelColor(Color.BLACK)
                        setEntryLabelTextSize(14f)
                        setEntryLabelTypeface(Typeface.DEFAULT_BOLD)

                        val pieDataSet = PieDataSet(pieEntries, "")
                        pieDataSet.colors = listOf(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED)


                        legend.apply {
                            isEnabled = true
                            textSize = 18f
                            textColor = Color.BLACK
                            typeface = Typeface.DEFAULT_BOLD
                            verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                            orientation = Legend.LegendOrientation.VERTICAL
                            setDrawInside(false)
                            xEntrySpace = 20f
                            yEntrySpace = 10f
                            yOffset = 0f
                            xOffset = 0f
                        }
                    }
                },
                update = { chart ->
                    val pieDataSet = PieDataSet(pieEntries, "")
                    pieDataSet.colors = listOf(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED)

                    val pieData = PieData(pieDataSet)
                    chart.data = pieData
                    chart.data.setValueTextSize(13f)
                    chart.notifyDataSetChanged()
                }
            )
        }
    }
}

@Composable
fun HorizontalBarChart() {
    val dataPoints = listOf(
        Pair("January", 25f),
        Pair("February", 30f),
        Pair("March", 15f),
        Pair("April", 50f),
        Pair("May", 40f),
        Pair("June", 20f)
    )

    val barEntries = dataPoints.mapIndexed { index, data ->
        BarEntry(index.toFloat(), data.second)
    }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = androidx.compose.ui.graphics.Color.Blue,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    HorizontalBarChart(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setDrawBarShadow(false)
                        setDrawValueAboveBar(true)
                        description.isEnabled = false
                        setMaxVisibleValueCount(60)
                        setPinchZoom(false)
                        setDrawGridBackground(false)

                        val barDataSet = BarDataSet(barEntries, "")
                        barDataSet.colors = listOf(Color.GREEN)

                        val barData = BarData(barDataSet)
                        data = barData

                        axisLeft.isEnabled = false
                        axisRight.isEnabled = false
                        xAxis.setDrawGridLines(false)
                        xAxis.granularity = 1f
                        xAxis.valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return dataPoints[value.toInt()].first
                            }
                        }
                        legend.isEnabled = false
                    }
                },
                update = { chart ->
                    val barDataSet = BarDataSet(barEntries, "")
                    barDataSet.colors = listOf(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED)

                    val barData = BarData(barDataSet)
                    chart.data = barData
                    chart.notifyDataSetChanged()
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
