package com.example.digiit.graphs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.digiit.R

val icons = listOf<TypeGraph>(
    TypeGraph.LineChart,
    TypeGraph.CubicLine,
    TypeGraph.BarChart,
    TypeGraph.BubbleGraph,
    TypeGraph.GroupedBarChart,
    TypeGraph.PieChart,
    TypeGraph.RadarChartData,
    TypeGraph.HorizontalBarChart)

@Composable
fun DialogGraph(setShowDialog: (Boolean) -> Unit) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colors.background
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { setShowDialog(false) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sélectionner un type de graphique à ajouter: ",
                        fontSize = 27.sp
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    repeat(4) { index ->
                        val firstIconIndex = index * 2
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                ExtendedFloatingActionButton(
                                    backgroundColor = MaterialTheme.colors.primary,
                                    onClick = { /* ... */ },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = icons[firstIconIndex].icon),
                                            contentDescription = "Favorite",
                                            tint = Color.White
                                        )
                                    },
                                    text = {
                                        Text(
                                            icons[firstIconIndex].title,
                                            color = Color.White
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.padding(5.dp))
                                val secondIconIndex = firstIconIndex + 1
                                ExtendedFloatingActionButton(
                                    backgroundColor = MaterialTheme.colors.primary,
                                    onClick = { /* ... */ },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = icons[secondIconIndex].icon),
                                            contentDescription = "Favorite",
                                            tint = Color.White
                                        )
                                    },
                                    text = {
                                        Text(
                                            icons[secondIconIndex].title,
                                            color = Color.White
                                        )
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}
