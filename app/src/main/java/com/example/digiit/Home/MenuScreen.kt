package com.example.digiit.Home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.R

@Composable
fun MenuScreen() {
    Scaffold(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxSize(),
        content = { padding ->
            MenuContent(padding)
        }, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Menu",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Logo")
                    }
                },

                contentColor = Color.White,
                elevation = 12.dp
            )
        }
    )
}

@Composable
fun MenuContent(paddingValues: PaddingValues) {
    Column(verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.surface,
            border = BorderStroke(1.dp,Color.Gray),
            modifier = Modifier
                .padding(horizontal = 36.dp, vertical = 16.dp)
                .align(Alignment.CenterHorizontally).fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(onClick = {},
                        Modifier.width(450.dp),
                        shape = RoundedCornerShape(180.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF009DDB))) {
                        Icon(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Logo profile",
                            modifier = Modifier.padding(5.dp),
                            tint = Color.White
                        )
                        Text(text = "Mon compte", color = Color.White, fontSize = 16.sp)
                    }
                }
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(onClick = {},
                        Modifier.width(450.dp),
                        shape = RoundedCornerShape(180.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0083E7))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.safety),
                            contentDescription = "Logo profile",
                            modifier = Modifier.padding(5.dp),
                            tint = Color.White
                        )
                        Text(text = "Confidentialité", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.surface,
            border = BorderStroke(1.dp,Color.Gray),
            modifier = Modifier.padding(horizontal = 36.dp, vertical = 16.dp)
                .align(Alignment.CenterHorizontally).fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(onClick = {},
                        Modifier.width(450.dp),
                        shape = RoundedCornerShape(180.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF056CE9))) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings),
                            contentDescription = "Logo reglages",
                            modifier = Modifier.padding(5.dp),
                            tint = Color.White
                        )
                        Text(text = "Réglages", color = Color.White, fontSize = 16.sp)
                    }
                }
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(onClick = {},
                        Modifier.width(450.dp),
                        shape = RoundedCornerShape(180.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0638EB))) {
                        Icon(
                            painter = painterResource(id = R.drawable.folders),
                            contentDescription = "Logo confidentialite",
                            modifier = Modifier.padding(5.dp),
                            tint = Color.White
                        )
                        Text(text = "Mes dossiers", color = Color.White, fontSize = 16.sp)
                    }
                }
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(onClick = {},
                        Modifier.width(450.dp),
                        shape = RoundedCornerShape(180.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0118EB))) {
                        Icon(
                            painter = painterResource(id = R.drawable.style),
                            contentDescription = "Logo personnalisation",
                            modifier = Modifier.padding(5.dp),
                            tint = Color.White
                        )
                        Text(text = "Personnalisation", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
