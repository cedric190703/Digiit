package com.example.digiit.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.R
import com.example.digiit.cards.WalletCard
import com.example.digiit.data.UserProvider
import com.example.digiit.data.wallet.Wallet
import com.example.digiit.photos.SelectOption
import com.example.digiit.photos.TypeScreen
import com.example.digiit.scrollbar.scrollbar
import com.example.digiit.search.SearchViewHomeWallet
import es.dmoral.toasty.Toasty

@Composable
fun WalletScreen(auth: UserProvider) {
    val showDialog =  remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { padding ->
            WalletContent(padding, auth)
        }, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Portefeuille",
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
                }, user = auth.user, TypeScreen.Wallet)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletContent(paddingValues: PaddingValues, auth: UserProvider) {
    val context = LocalContext.current.applicationContext
    val userWallets = auth.user!!.wallets
    val listState = rememberLazyListState()
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()) {
        SearchViewHomeWallet(auth)
        if(userWallets.size == 0)
        {
            Spacer(modifier = Modifier.padding(15.dp))
            Image(painter = painterResource(id = R.drawable.wallet_image),
                contentDescription = "image for no data",
                modifier = Modifier
                    .width(380.dp)
                    .height(270.dp))
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Pas d'éléments",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                )
            }
        }
        else
        {
            LazyColumn(state = listState,
                modifier = Modifier.scrollbar(state = listState)) {
                itemsIndexed(userWallets) { _: Int, wallet: Wallet ->
                    val state= rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                userWallets.remove(wallet)
                                wallet.delete { err ->
                                    if (err == null) {
                                        Toasty.success(context, "Porte feuille supprimé", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toasty.error(context, "Error lors de la suppression du feuille", Toast.LENGTH_SHORT).show()
                                        userWallets.add(wallet)
                                    }
                                }
                                true
                            } else {
                                false
                            }
                        }
                    )
                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color = if (kotlin.math.abs(state.direction) > 0.1) when(state.dismissDirection){
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
                                    tint=Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }

                        },
                        dismissContent = {
                            WalletCard(wallet = wallet, auth = auth)
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                }
            }
        }
    }
}
