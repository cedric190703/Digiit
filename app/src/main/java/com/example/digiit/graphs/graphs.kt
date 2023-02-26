package com.example.digiit.graphs

import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import androidx.compose.material.Card
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

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
                    barDataSet.colors = listOf(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED)

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