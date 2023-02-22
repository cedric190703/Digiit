package com.example.digiit.Home

import com.example.digiit.Cards.tags
import com.example.digiit.Cards.ticketsCard
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.DialogDelete
import com.example.digiit.R
import com.example.digiit.photos.SelectOption
import com.example.digiit.scrollbar
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
                SelectOption(setShowDialog = {
                    showDialog.value = it
                })
        }
    )
}

data class ticket(val typeCommerce: tags,
                  val tag: String,
                  val titre: String,
                  val prix: Int,
                  val dateTime: String,
                  val dateDate: String,
                  val colorIcon: Color,
                  val colorTag: Color,
                  val colorText: Color,
                  val rating: Int,
                  val comment: String)

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
                                showDialog.value = true
                                idx.value = listTickets.indexOf(item)
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
                            ticketsCard(ticket = item)
                        },
                        directions=setOf(DismissDirection.EndToStart)
                    )
                }
            }
            if(showDialog.value)
            {
                DialogDelete(idx = idx.value ,Tickets = listTickets ,onDismiss = {
                    showDialog.value = it
                })
            }
        }
    }
}