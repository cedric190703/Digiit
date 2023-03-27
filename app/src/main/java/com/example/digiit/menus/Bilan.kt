package com.example.digiit.menus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.digiit.graphs.CubicLineChart
import com.example.digiit.graphs.LineChart
import com.example.digiit.graphs.PieChart
import com.example.digiit.scrollbar.scrollbar
import com.example.digiit.utils.getCurrentMonth

@Composable
fun Bilan(onDismiss: (Boolean) -> Unit,
    dataOnMonth: Float, maxValueData: Float) {
    val listState = rememberLazyListState()
    Dialog(
        onDismissRequest = { onDismiss(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp
        ) {
            LazyColumn(state = listState,
                modifier = Modifier.scrollbar(state = listState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                item {
                    Text(
                        text = "Votre Bilan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                item {
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(4.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Dépenses : ${getCurrentMonth()}",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(
                                text = "$${dataOnMonth.toInt()}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(4.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Montant maximum",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(
                                text = "$${maxValueData.toInt()}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            )
                        }
                    }
                }
                item {
                    Text(text = "Dépenses des 4 secteurs les plus importants :",
                        modifier = Modifier.padding(start = 18.dp, top = 12.dp))
                    PieChart()
                }
                item {
                    Text(text = "Dépenses des 4 derniers mois :")
                    LineChart()
                }
                item {
                    Text(text = "Dépenses avec les 4 types de documents les plus importants :",
                        modifier = Modifier.padding(start = 18.dp, top = 12.dp))
                    CubicLineChart()
                }
                item {
                    Button(
                        onClick = { onDismiss(false) },
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp)
                            .fillMaxHeight()
                            .width(120.dp)
                    ) {
                        Text("OK", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}