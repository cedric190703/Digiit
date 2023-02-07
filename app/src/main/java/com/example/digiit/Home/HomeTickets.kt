package com.example.digiit.Home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxWidth()) {
        SearchView()
        Box() {
            Image(painter = painterResource(id = R.drawable.tickets_image),
                contentDescription = "tickets image",
                Modifier.size(350.dp).align(Alignment.TopCenter))
            Text(
                "Pas de tickets",
                fontSize = 34.sp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

//Test to see image
@Composable
fun SearchView() {
    val searchText = remember { mutableStateOf("") }
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
            value = searchText.value,
            onValueChange = { searchText.value = it },
            label = { Text("Search") },
            modifier = Modifier
                .padding(10.dp)
                .width(250.dp),
        )
        Button(
            onClick = { },
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = "search logo")
        }
    }
}
