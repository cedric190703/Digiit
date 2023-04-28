package com.example.digiit.graphs

import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.UserProvider
import com.example.digiit.data.user.User
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.resumeWithException

val top5Color = listOf(Color.rgb(85, 216, 254), Color.rgb(255, 131, 115), Color.rgb(255, 218, 131), Color.rgb(163, 160, 251), Color.rgb(90, 219, 168) )


enum class Mois(val numero: Int, val nom: String) {
    JANVIER(1, "janvier"),
    FEVRIER(2, "février"),
    MARS(3, "mars"),
    AVRIL(4, "avril"),
    MAI(5, "mai"),
    JUIN(6, "juin"),
    JUILLET(7, "juillet"),
    AOUT(8, "août"),
    SEPTEMBRE(9, "septembre"),
    OCTOBRE(10, "octobre"),
    NOVEMBRE(11, "novembre"),
    DECEMBRE(12, "décembre");

    companion object {
        private val map = values().associateBy(Mois::numero)
        fun getNomMois(numero: Int): String = map[numero]!!.nom
    }
}


@Composable
        /**
         * Generate the linechart graph with each months spending
         */
fun LineChartByMonth(auth: UserProvider, start : LocalDateTime, end :  LocalDateTime) {

    val dataGraph = mutableListOf<Pair<LocalDateTime, Float>>()
    var current = start.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS)
    var endOfMonth = current
    while (!current.isAfter(end)) {
        var x = 0f
        // need to include the last day of months so taking first day 0:0:0 (will be exclude in the getSpending methode)
        endOfMonth = current.plusMonths(1).withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS)
        auth.user!!.getSpending(null,current, endOfMonth)
            { error, spending ->
                if (error != null) {
                    println("Une erreur est survenue : ${error.message}")
                } else {
                    x = spending
                }
            }
        dataGraph.add(Pair(current, x))
        current = endOfMonth
    }
    val lineEntries = dataGraph.mapIndexed { index, data ->
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
                                    return Mois.getNomMois(dataGraph[value.toInt()].first.monthValue)
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
}@OptIn(ExperimentalCoroutinesApi::class)
suspend fun getTopKinds(user: User, after : LocalDateTime = LocalDateTime.MIN, before : LocalDateTime = LocalDateTime.MAX): List<Pair<TradeKinds, Float>> {
    val result = ArrayList<Pair<TradeKinds, Float>>()
    for (kind in TradeKinds.values()) {
        result.add(Pair(kind, suspendCancellableCoroutine { continuation ->
            user.getSpending(kind, after, before) { error, amount ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else {
                    continuation.resume(amount, null)
                }
            }
        }))
    }
    return result.sortedByDescending { it.second }
}

/*suspend fun getGraphData(user: User, after : LocalDateTime = LocalDateTime.MIN, before : LocalDateTime = LocalDateTime.MAX): PieData {
    val top5 = getTopKinds(user, after, before).take(5)
    val dataPoints = top5.sortedByDescending { it.second }.take(5)
    val pieEntries = dataPoints.map { data ->
        PieEntry(data.second, data.first.title + " "+ data.second + "$")
    }

    val pieDataSet = PieDataSet(pieEntries, "")
    pieDataSet.colors = top5Color

    return PieData(pieDataSet)
}*/


@Composable
//PieChar With 5 most used kinds between two date
fun PieChart(auth : UserProvider, after : LocalDateTime = LocalDateTime.MIN, before : LocalDateTime = LocalDateTime.MAX) {
    val top5= if(ListOfType.size==0)
        TradeKinds.values().map {kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    else
        ListOfType.map{ kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    val dataPoints = if(ListOfType.size!=0) top5.sortedByDescending { it.second}.take(ListOfType.size)
    else top5.sortedByDescending { it.second}.take(5)

    /*val dataPoints = listOf(
        Pair("Commerce", 25f),
        Pair("Sport", 30f),
        Pair("Alimentations", 15f),
        Pair("Culture", 50f),
    )*/

    val pieEntries = dataPoints.mapIndexed { _, data ->
        PieEntry(data.second, data.first.title)
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
                            textSize = 12f
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
fun BarChart( auth: UserProvider, after : LocalDateTime = LocalDateTime.MIN, before : LocalDateTime = LocalDateTime.MAX) {
    val top5= if(ListOfType.size==0)
        TradeKinds.values().map {kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    else
        ListOfType.map{ kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    val dataPoints = if(ListOfType.size!=0) top5.sortedByDescending { it.second}.take(ListOfType.size)
    else top5.sortedByDescending { it.second}.take(5)

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
    ){
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
                        barDataSet.colors = top5Color

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
                                    return if (value.toInt() < dataPoints.size && value.toInt() >= 0) {
                                        dataPoints[value.toInt()].first.toString()
                                    } else {
                                        ""
                                    }
                                }
                            }
                        }
                        legend.isEnabled = false
                    }
                },
                update = { chart ->
                    val barDataSet = BarDataSet(barEntries, "")
                    barDataSet.colors = top5Color

                    val barData = BarData(barDataSet)
                    chart.data = barData
                    chart.notifyDataSetChanged()
                },
            )
        }
    }
}

@Composable
fun LineChart(auth : UserProvider, after : LocalDateTime = LocalDateTime.MIN, before : LocalDateTime = LocalDateTime.MAX) {
    val top5= if(ListOfType.size==0)
        TradeKinds.values().map {kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    else
        ListOfType.map{ kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    val dataPoints = if(ListOfType.size!=0) top5.sortedByDescending { it.second}.take(ListOfType.size)
    else top5.sortedByDescending { it.second}.take(5)

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
                                    return if (value.toInt() < dataPoints.size && value.toInt() >= 0) {
                                        dataPoints[value.toInt()].first.toString()
                                    } else {
                                        ""
                                    }
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
//PieChar With 5 most used kinds between two date
fun CubicLineChart( auth: UserProvider, after : LocalDateTime = LocalDateTime.MIN, before : LocalDateTime = LocalDateTime.MAX) {
    val top5= if(ListOfType.size==0)
        TradeKinds.values().map {kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    else
    ListOfType.map{ kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    val dataPoints = if(ListOfType.size!=0) top5.sortedByDescending { it.second}.take(ListOfType.size)
    else top5.sortedByDescending { it.second}.take(5)

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
                        dataSet.circleRadius = 6f
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
                                    return if (value.toInt() < dataPoints.size && value.toInt() >= 0) {
                                        dataPoints[value.toInt()].first.toString()
                                    } else {
                                        ""
                                    }
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
    dataSet1.color = top5Color[1]
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
    dataSet2.color = top5Color[2]
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
    dataSet3.color = top5Color[3]
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
fun HorizontalBarChart(auth : UserProvider, after : LocalDateTime = LocalDateTime.MIN, before : LocalDateTime = LocalDateTime.MAX) {
    val top5= if(ListOfType.size==0)
        TradeKinds.values().map {kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    else
        ListOfType.map{ kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    val dataPoints = if(ListOfType.size!=0) top5.sortedByDescending { it.second}.take(ListOfType.size)
    else top5.sortedByDescending { it.second}.take(5)

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
                        barDataSet.colors = top5Color

                        val barData = BarData(barDataSet)
                        data = barData

                        axisLeft.isEnabled = false
                        axisRight.isEnabled = false
                        xAxis.setDrawGridLines(false)
                        xAxis.granularity = 1f
                        xAxis.valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return if (value.toInt() < dataPoints.size && value.toInt() >= 0) {
                                        dataPoints[value.toInt()].first.toString()
                                    } else {
                                        ""
                                    }
                                }
                        }
                        legend.isEnabled = false
                    }
                },
                update = { chart ->
                    val barDataSet = BarDataSet(barEntries, "")
                    barDataSet.colors = top5Color

                    val barData = BarData(barDataSet)
                    chart.data = barData
                    chart.notifyDataSetChanged()
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun BubbleChart(auth : UserProvider, start : LocalDateTime = LocalDateTime.MIN, end : LocalDateTime = LocalDateTime.MAX) {
    val months = mutableListOf<LocalDateTime>()

    // (date, averageRating, spending)
    val dataGraph = mutableListOf<Triple<LocalDateTime, Float, Float>>()

    var current = start.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS)
    var endOfMonth = current

    while (!current.isAfter(end)) {
        months.add(current)

        // need to include the last day of months so taking first day 0:0:0 (will be exclude in the getSpending methode)
        endOfMonth = current.plusMonths(1).withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS)

        for (tradekind in TradeKinds.values()) {
            val (spending, averageRating) = auth.user!!.getSpendingWithRating(tradekind, start, end)
            dataGraph.add(Triple(current, averageRating, spending))
        }
        current = endOfMonth
    }

    val entriesByMonth = mutableMapOf<Int, MutableList<BubbleEntry>>()
    for (i in 0 until months.size) {
        entriesByMonth[i] = mutableListOf()
    }
    for (triple in dataGraph) {
        val monthIndex = months.indexOf(triple.first.withDayOfMonth(1))
        if (monthIndex != -1) {
            entriesByMonth[monthIndex]?.add(BubbleEntry(
                monthIndex.toFloat(),
                triple.second,
                triple.third
            ))
        }
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
                    BubbleChart(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setDrawGridBackground(false)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        setPinchZoom(true)

                        val bubbleDataSets = mutableListOf<BubbleDataSet>()

                        for ((monthIndex, entries) in entriesByMonth) {
                            val dataSet = BubbleDataSet(entries, "Month $monthIndex")
                            dataSet.colors = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN)
                            dataSet.valueTextSize = 12f
                            bubbleDataSets.add(dataSet)
                        }

                        val bubbleData = BubbleData(bubbleDataSets as List<IBubbleDataSet>?)
                        data = bubbleData

                        xAxis.apply {
                            textSize = 12f
                            typeface = Typeface.DEFAULT_BOLD
                            axisMinimum = -0.5f
                            axisMaximum = dataGraph.size - 0.5f
                            position = XAxisPosition.BOTTOM
                            granularity = 1f
                            setDrawGridLines(false)
                            val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH)

                            valueFormatter = object : ValueFormatter() {
                                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                                    val month = months.getOrNull(value.toInt())
                                    if (month != null) {
                                        return formatter.format(month)
                                    } else {
                                        return ""
                                    }
                                }
                            }
                        }

                        axisLeft.apply {
                            axisMinimum = 0f
                            axisMaximum = 5.4f
                        }

                        axisRight.isEnabled = false
                        legend.isEnabled = false
                    }
                },
                update = { chart ->
                    val bubbleDataSets = mutableListOf<BubbleDataSet>()

                    for ((monthIndex, entries) in entriesByMonth) {
                        val dataSet = BubbleDataSet(entries, "Month $monthIndex")
                        dataSet.colors = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN)
                        dataSet.valueTextSize = 12f
                        bubbleDataSets.add(dataSet)
                    }

                    val bubbleData = BubbleData(bubbleDataSets as List<IBubbleDataSet>?)
                    chart.data = bubbleData
                    chart.notifyDataSetChanged()
                }
            )
        }
    }
}

@Composable
fun RadarChart( auth: UserProvider, after : LocalDateTime = LocalDateTime.MIN, before : LocalDateTime = LocalDateTime.MAX) {
    val top5= if(ListOfType.size==0)
        TradeKinds.values().map {kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    else
        ListOfType.map{ kind -> Pair(kind,  auth.user!!.getSpeedingIn(kind,after,before))}
    val dataPoints = if(ListOfType.size!=0) top5.sortedByDescending { it.second}.take(ListOfType.size)
    else top5.sortedByDescending { it.second}.take(5)

    val maxValue = dataPoints.maxByOrNull { it.second }?.second ?: 0f
    val yAxisMaxValue = (maxValue / 10f).coerceAtLeast(1f) * 10f
    /*var tavue=0
    for(s in dataPoints)
    {
        tavue=tavue+s.second.toInt()
    }
    for (s in 0 until dataPoints.size) {
        var pute=dataPoints[s].second.toInt()
        var final=(pute / tavue)*100
        dataPoints[s]=final
    }*/

    val radarEntries = dataPoints.mapIndexed { index, data ->
        RadarEntry(data.second).apply {
            dataPoints[index].first
        }
    }
    Card(
        modifier = Modifier
            .padding(25.dp)
            .border(
                width = 1.dp,
                color = androidx.compose.ui.graphics.Color.Blue,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .height(340.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)) {
            AndroidView(
                factory = { context ->
                    RadarChart(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setTouchEnabled(true)
                        webLineWidth = 1f
                        webColor = Color.GRAY
                        webLineWidthInner = 1f
                        webColorInner = Color.GRAY
                        webAlpha = 100

                        val radarDataSet = RadarDataSet(radarEntries, "Stats")
                        radarDataSet.color = Color.BLUE
                        radarDataSet.fillColor = Color.BLUE
                        radarDataSet.setDrawFilled(true)
                        radarDataSet.lineWidth = 2f

                        val radarData = RadarData(listOf<IRadarDataSet>(radarDataSet))
                        data = radarData
                        xAxis.apply {
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return if (value.toInt() < dataPoints.size && value.toInt() >= 0) {
                                        dataPoints[value.toInt()].first.toString()
                                    } else {
                                        ""
                                    }
                                }
                            }
                            textSize = 10f
                            setLabelCount(dataPoints.size, true) // pour afficher tous les labels
                            setAvoidFirstLastClipping(true)
                        }
                        yAxis.apply {
                            setDrawLabels(true)
                            setAxisMinValue(0f)
                            setAxisMaxValue((yAxisMaxValue/140)*100)
                        }
                        legend.isEnabled = false
                        description.isEnabled = false
                    }
                },
                update = { chart ->
                    chart.data.notifyDataChanged()
                    chart.data.setValueTextSize(13f)
                    chart.notifyDataSetChanged()
                }
            )
        }
    }
}