package com.example.digiit.Home

import Cards.tags
import Cards.ticketsCard
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun HomeScreen() {
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
                onClick = {},
                Modifier.size(70.dp),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
            ) {
                Icon(Icons.Filled.Add,
                    "add button",
                    modifier = Modifier.fillMaxSize(0.5F))
            }
        }
    )
}

@Composable
fun HomeTicketContent(paddingValues: PaddingValues) {
    val formatterDate = SimpleDateFormat("dd-MM-yyyy")
    val formatterTime = SimpleDateFormat("HH:mm")
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxWidth()) {
        SearchView()

        LazyColumn {
            item {
                ticketsCard(typeCommerce = tags.Alimentation,
                    tag = "Test",
                    titre = "Carrefour",
                    prix = 42,
                    dateTime = formatterTime.format(Date()),
                    dateDate = formatterDate.format(Date()),
                    colorIcon = Color.Blue,
                    colorTag = Color.Red,
                    colorText = Color.Black)
            }
            item {
                ticketsCard(typeCommerce = tags.Artisan,
                    tag = "Test2",
                    titre = "Wallemart",
                    prix = 42,
                    dateTime = formatterTime.format(Date()),
                    dateDate = formatterDate.format(Date()),
                    colorIcon = Color.Gray,
                    colorTag = Color.Blue,
                    colorText = Color.Black)
            }
            item {
                ticketsCard(typeCommerce = tags.Culture,
                    tag = "Test3",
                    titre = "U&W",
                    prix = 42000,
                    dateTime = formatterTime.format(Date()),
                    dateDate = formatterDate.format(Date()),
                    colorIcon = Color.Red,
                    colorTag = Color.Blue,
                    colorText = Color.Black)
            }
            item {
                ticketsCard(typeCommerce = tags.Habillement,
                    tag = "Test3",
                    titre = "U&W",
                    prix = 42000,
                    dateTime = formatterTime.format(Date()),
                    dateDate = formatterDate.format(Date()),
                    colorIcon = Color.Black,
                    colorTag = Color.DarkGray,
                    colorText = Color.Black)
            }
        }
    }
}

@Composable
fun SearchView() {
    var searchText by remember { mutableStateOf("") }
    val isVisible by remember {
        derivedStateOf {
            searchText.isNotBlank()
        }
    }
    Row (modifier = Modifier
        .padding(top = 10.dp, bottom = 20.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.account),
            contentDescription = "profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .padding(8.dp)
        )
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            modifier = Modifier
                .padding(10.dp)
                .width(250.dp),
            leadingIcon = { Icon(Icons.Filled.Search, "search icon") },
            trailingIcon = {
                if (isVisible) {
                    IconButton(
                        onClick = { searchText = "" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
        )
        Button(
            onClick = { },
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.filter),
                contentDescription = "search logo")
        }
    }
}
