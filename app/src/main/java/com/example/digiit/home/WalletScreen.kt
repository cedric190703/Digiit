package com.example.digiit.home

import android.hardware.usb.UsbRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.R
import com.example.digiit.cards.WalletsCard
import com.example.digiit.data.UserProvider
import com.example.digiit.data.user.User
import com.example.digiit.scrollbar.scrollbar
import com.example.digiit.search.SearchViewHomeWallet


@Composable
fun WalletScreen(auth: UserProvider) {
    Scaffold(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxSize(),
        content = { padding ->
            WalletContent(padding, auth)
        }, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Wallet",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.wallet),
                            contentDescription = "Logo Wallet"
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletContent(paddingValues: PaddingValues, auth: UserProvider) {
    var listWallets = auth.user!!.wallets
    val listState = rememberLazyListState()
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()) {
        SearchViewHomeWallet(auth)
        if(listWallets.size == 0)
        {
            Spacer(modifier = Modifier.padding(20.dp))
            Image(painter = painterResource(id = R.drawable.wallet_image),
                contentDescription = "image for no data")
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Pas d'éléments",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                )
            }
        }
        else
        {
            LazyColumn(state = listState,
                modifier = Modifier.scrollbar(state = listState)) {
                items(listWallets.size) { item ->
                    val state= rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart){
                                // Remove the wallet with this when the user is imported in this function
                                // Wallet to implement
                                // wallet.remove(item)
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
                            WalletsCard(wallet = listWallets[item])
                        },
                        directions=setOf(DismissDirection.EndToStart)
                    )
                }
            }
        }
    }
}
