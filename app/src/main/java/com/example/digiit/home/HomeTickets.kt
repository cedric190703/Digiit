package com.example.digiit.home

import com.example.digiit.data.TradeKinds
import com.example.digiit.cards.TicketsCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.R
import com.example.digiit.photos.selectOption
import com.example.digiit.scrollbar.scrollbar
import com.example.digiit.search.SearchViewHomeTicket

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val showDialog =  remember { mutableStateOf(false) }
    Scaffold(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxSize(),
        content = { padding ->
            HomeTicketContent(padding)
        }, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tickets",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.tickets),
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
                Icon(Icons.Filled.Add,
                    "add button",
                    modifier = Modifier.fillMaxSize(0.5F))
            }
            if(showDialog.value)
                selectOption(setShowDialog = {
                    showDialog.value = it
                })
        }
    )
}

data class ticket(
    var typeCommerce: TradeKinds,
    var tag: String,
    var titre: String,
    var prix: Int,
    var dateTime: String,
    var dateDate: String,
    var colorIcon: Color,
    var colorTag: Color,
    var colorText: Color,
    var rating: Int,
    var comment: String,
    var painter: Painter
)

var listTickets = mutableStateListOf<ticket>()

@ExperimentalMaterialApi
@Composable
fun HomeTicketContent(paddingValues: PaddingValues) {
    val listState = rememberLazyListState()
    val showDialog = remember { mutableStateOf(false) }
    val idx = remember { mutableStateOf(0)}
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxWidth()) {
        SearchViewHomeTicket()
        if(listTickets.size == 0)
        {
            Image(painter = painterResource(id = R.drawable.tickets_image),
                contentDescription = "image for no data")
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Pas de tickets",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                )
            }
        }
        else
        {
            LazyColumn(state = listState,
                modifier = Modifier.scrollbar(state = listState)) {
                items(listTickets) { item ->
                    val state= rememberDismissState(
                        confirmStateChange = {
                            if (it==DismissValue.DismissedToStart){
                                listTickets.remove(item)
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color=when(state.dismissDirection){
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint=Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }

                        },
                        dismissContent = {
                            TicketsCard(ticket = item)
                        },
                        directions=setOf(DismissDirection.EndToStart)
                    )
                }
            }
        }
    }
}