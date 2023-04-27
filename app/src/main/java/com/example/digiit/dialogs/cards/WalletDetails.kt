package com.example.digiit.dialogs.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.digiit.ApplicationData
import com.example.digiit.cards.DeleteCard
import com.example.digiit.cards.EditCard
import com.example.digiit.data.wallet.Wallet
import com.example.digiit.photos.TypeScreen
import com.example.digiit.utils.BarcodeImage
import com.example.digiit.widgets.AsyncImage
import java.time.format.DateTimeFormatter


@Composable
fun WalletDetails(setState: (Boolean) -> Unit, wallet: Wallet, auth: ApplicationData) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    val showEditDialog = remember { mutableStateOf(false) }
    val bigMode = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { setState(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = wallet.colorTag) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.verticalScroll(
                    rememberScrollState(),
                    enabled = bigMode.value
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {
                        setState(false)
                    }) {
                        Icon(imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.size(38.dp))
                    }
                }

                if (bigMode.value) {
                    Spacer(modifier = Modifier.padding(6.dp))
                    AsyncImage(
                        modifier = Modifier
                            .padding(17.dp)
                            .size(350.dp)
                    ) { callback ->
                        wallet.loadImage {
                            callback(wallet.getImageBitmapOrDefault())
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = "${wallet.price}$",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = wallet.title,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                if (bigMode.value) {
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "#${wallet.tag}",
                        color = Color.White,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.padding(12.dp))
                // TODO : Change here with the barCode from the wallet
                BarcodeImage(number = "0123456790")

                Spacer(modifier = Modifier.padding(72.dp))
                ExtendedFloatingActionButton(
                    modifier = Modifier.height(65.dp),
                    text = {  Text(text = wallet.type.title,
                        fontSize = 17.sp, color = Color.White) },
                    backgroundColor = Color.Transparent,
                    onClick = {  },
                    icon = {
                        Icon(painter = painterResource(wallet.type.icon),
                            "wallet type logo",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(35.dp),
                            tint = Color.White)
                    }
                )

                Spacer(modifier = Modifier.padding(8.dp))
                ExtendedFloatingActionButton(
                    modifier = Modifier.height(65.dp),
                    text = {  Text(text = wallet.walletType.title,
                        fontSize = 17.sp, color = Color.White) },
                    backgroundColor = Color.Transparent,
                    onClick = {  },
                    icon = {
                        Icon(painter = painterResource(wallet.walletType.icon),
                            "wallet commercial type logo",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(35.dp),
                            tint = Color.White)
                    }
                )

                Spacer(modifier = Modifier.padding(12.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(start = 18.dp),
                            text = "Effectué le :  ",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            modifier = Modifier.padding(vertical = 5.dp),
                            text = wallet.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(start = 18.dp),
                            text = "Effectué à :     ",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            modifier = Modifier.padding(start = 18.dp),
                            text = wallet.date.format(DateTimeFormatter.ofPattern("HH:mm")),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(start = 18.dp),
                            text = "Date expiration :",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            modifier = Modifier.padding(vertical = 5.dp),
                            text = wallet.expiryDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (bigMode.value) {
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = wallet.comment,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (!bigMode.value) {
                    Spacer(modifier = Modifier.padding(12.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 5.dp),
                        text = { Text(text = "Plus de détails", fontSize = 18.sp) },
                        onClick = {
                            bigMode.value = true
                        },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                }

                Spacer(modifier = Modifier.padding(6.dp))
                Row() {
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 5.dp),
                        text = {  Text(text = "Modifier", fontSize = 18.sp) },
                        onClick = { showEditDialog.value = true },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 5.dp),
                        text = {  Text(text = "Supprimer", fontSize = 18.sp) },
                        onClick = { showDeleteDialog.value = true },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                }

                if(showDeleteDialog.value)
                {
                    DeleteCard(wallet, onDismiss = {deleted ->
                        showDeleteDialog.value = false
                        setState(!deleted)
                    }, auth = auth)
                }

                if(showEditDialog.value)
                {
                    EditCard(
                        wallet,
                        setShowDialog = {
                            showEditDialog.value = it
                        },
                        setView = {
                            setState(it)
                        }, true, TypeScreen.Wallet
                    )
                }
            }
        }
    }
}
