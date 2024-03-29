package com.example.digiit.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.R
import com.example.digiit.data.UserProvider
import com.example.digiit.graphs.*
import com.example.digiit.scrollbar.scrollbar

@Composable
fun DataScreen(auth: UserProvider) {
    val showDialog = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { padding ->
            DataContent(padding, auth)
        }, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Data",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.data),
                            contentDescription = "Logo Home"
                        )
                    }
                },

                contentColor = Color.White,
                elevation = 12.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog.value = true },
                Modifier.size(70.dp),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
            ) {
                Icon(painter = painterResource(id = R.drawable.add_chart),
                    "add button",
                    modifier = Modifier.fillMaxSize(0.5F))
            }
        }
    )
    if(showDialog.value)
    {
        DialogGraph( auth,setShowDialog = {
            showDialog.value = it
        })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataContent(paddingValues: PaddingValues, auth: UserProvider) {
    val listState = rememberLazyListState()
    val tickets=auth.user!!.tickets
    val listGraphs=auth.user!!.listGraphs
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.padding(6.dp))
        Icon(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            tint = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = auth.user?.name.toString(),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = auth.user?.email.toString(),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        if(tickets.size == 0)
        {
            Image(painter = painterResource(id = R.drawable.data_image),
                contentDescription = "image for no data",
                modifier = Modifier
                    .width(350.dp)
                    .height(250.dp))
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Pas encore de données",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                )
            }
        }
        else if(listGraphs.size==0){
            Image(painter = painterResource(id = R.drawable.data_image),
                contentDescription = "image for no data",
                modifier = Modifier
                    .width(350.dp)
                    .height(250.dp))
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = buildAnnotatedString {
                        append("Ajouter un ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("graphe")
                        }
                    },
                    fontSize = 30.sp,
                )
            }
        }
        else {
            LazyColumn(
                state = listState,
                modifier = Modifier.scrollbar(state = listState)
            ) {
                itemsIndexed(listGraphs) { _: Int, item : TypeGraph ->
                    val state = DismissState(
                        initialValue = DismissValue.Default,
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                listGraphs.remove(item)
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color =
                                if (kotlin.math.abs(state.direction) > 0.1) when (state.dismissDirection) {
                                    DismissDirection.StartToEnd -> Color.Transparent
                                    DismissDirection.EndToStart -> Color.Red
                                    null -> Color.Transparent
                                } else Color.Transparent
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            when (item) {
                                TypeGraph.PieChart -> PieChart(auth, StartDate, EndDate)
                                TypeGraph.RadarChartData -> RadarChart(auth, StartDate, EndDate)
                                TypeGraph.LineChart -> LineChart(auth, StartDate, EndDate)
                                TypeGraph.GroupedBarChart -> GroupedBarChart()
                                TypeGraph.BarChart -> BarChart(auth, StartDate, EndDate)
                                TypeGraph.BubbleGraph -> BubbleChart(auth, StartDate, EndDate)
                                TypeGraph.CubicLine -> CubicLineChart(auth, StartDate, EndDate)
                                TypeGraph.HorizontalBarChart -> HorizontalBarChart(auth, StartDate, EndDate)
                            }
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                }
            }
        }
    }
}