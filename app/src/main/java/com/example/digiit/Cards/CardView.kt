package com.example.digiit.Cards

import androidx.compose.foundation.Image
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
import com.example.digiit.DialogDelete
import com.example.digiit.Home.listTickets
import com.example.digiit.Home.ticket
import com.example.digiit.R
import com.mahmoudalim.compose_rating_bar.RatingBarView

@Composable
fun cardViewSmall(setState: (Boolean) -> Unit, ticket: ticket) {
    val ratingVal = remember { mutableStateOf(ticket.rating) }
    val showDialog = remember { mutableStateOf(false) }
    val dialogModif = remember { mutableStateOf(false) }
    val bigScreen = remember { mutableStateOf(false) }
    Dialog(onDismissRequest = { setState(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = ticket.colorIcon) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { setState(false) }) {
                            Icon(imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White,
                                modifier = Modifier.size(38.dp))
                        }
                    }
                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "${ticket.prix}$",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "${ticket.titre}",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.height(65.dp),
                        text = {  Text(text = ticket.typeCommerce.title,
                            fontSize = 17.sp, color = Color.White) },
                        backgroundColor = Color.Transparent,
                        onClick = {  },
                        icon = {
                            Icon(painter = painterResource(ticket.typeCommerce.icon),
                                "Logo type ticket",
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
                                modifier = Modifier.padding(vertical = 5.dp),
                                text = "Effectué le :",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.padding(20.dp))
                            Text(
                                modifier = Modifier.padding(vertical = 5.dp),
                                text = "${ticket.dateDate}",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                modifier = Modifier.padding(vertical = 5.dp),
                                text = "Effectué à :     ",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.padding(20.dp))
                            Text(
                                modifier = Modifier.padding(vertical = 5.dp),
                                text = "${ticket.dateTime}",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(12.dp))
                    RatingBarView(
                        rating = ratingVal,
                        isRatingEditable = false,
                        isViewAnimated = false,
                        ratedStarsColor = Color.White,
                        starIcon = painterResource(id = R.drawable.full_star),
                        unRatedStarsColor = Color.Gray,
                        starsPadding = 10.dp
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 5.dp),
                        text = {  Text(text = "Plus de détails", fontSize = 18.sp) },
                        onClick = {
                            bigScreen.value = true
                                  },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                    Row() {
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .height(85.dp)
                                .padding(vertical = 18.dp, horizontal = 5.dp),
                            text = {  Text(text = "Modifier", fontSize = 18.sp) },
                            onClick = { dialogModif.value = true },
                            backgroundColor = MaterialTheme.colors.primary
                        )
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .height(85.dp)
                                .padding(vertical = 18.dp, horizontal = 5.dp),
                            text = {  Text(text = "Supprimer", fontSize = 18.sp) },
                            onClick = { showDialog.value = true },
                            backgroundColor = MaterialTheme.colors.primary
                        )
                    }
                    if(showDialog.value)
                    {
                        DialogDelete(idx = listTickets.indexOf(ticket) ,Tickets = listTickets ,onDismiss = {
                            showDialog.value = it
                        })
                    }
                    if(dialogModif.value)
                    {
                        ModifElement(ticket = ticket,
                            setShowDialog = {
                                dialogModif.value = it
                            })
                    }
                    if(bigScreen.value)
                    {
                        cardViewBig(setState = {
                                               bigScreen.value = it
                        },
                            ticket = ticket,
                        setLittleDialog = {
                            setState(false)
                        })
                    }
                }
            }
    }
}

@Composable
fun cardViewBig(
    setState: (Boolean) -> Unit,
    ticket: ticket,
    setLittleDialog: (Boolean) -> Unit
    ) {
    val ratingVal = remember { mutableStateOf(ticket.rating) }
    val showDialog = remember { mutableStateOf(false) }
    val dialogModif = remember { mutableStateOf(false) }
    Dialog(onDismissRequest = { setState(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = ticket.colorIcon) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.verticalScroll(
                    rememberScrollState(),
                    enabled = true
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {
                        setState(false)
                    setLittleDialog(false)}) {
                        Icon(imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.size(38.dp))
                    }
                }
                Spacer(modifier = Modifier.padding(6.dp))
                Image(
                    painter = ticket.painter,
                    contentDescription = "photo taken",
                    modifier = Modifier
                        .padding(17.dp)
                        .size(350.dp)
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = "${ticket.prix}$",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = "${ticket.titre}",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = "#${ticket.tag}",
                    color = Color.White,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(6.dp))
                ExtendedFloatingActionButton(
                    modifier = Modifier.height(65.dp),
                    text = {  Text(text = ticket.typeCommerce.title,
                        fontSize = 17.sp, color = Color.White) },
                    backgroundColor = Color.Transparent,
                    onClick = {  },
                    icon = {
                        Icon(painter = painterResource(ticket.typeCommerce.icon),
                            "Logo type ticket",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(35.dp),
                            tint = Color.White)
                    }
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(vertical = 5.dp),
                            text = "Effectué le :",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            modifier = Modifier.padding(vertical = 5.dp),
                            text = "${ticket.dateDate}",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(vertical = 5.dp),
                            text = "Effectué à :     ",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            modifier = Modifier.padding(vertical = 5.dp),
                            text = "${ticket.dateTime}",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = "${ticket.comment}",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(6.dp))
                RatingBarView(
                    rating = ratingVal,
                    isRatingEditable = false,
                    isViewAnimated = false,
                    ratedStarsColor = Color.White,
                    starIcon = painterResource(id = R.drawable.full_star),
                    unRatedStarsColor = Color.Gray,
                    starsPadding = 10.dp
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Row() {
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 5.dp),
                        text = {  Text(text = "Modifier", fontSize = 18.sp) },
                        onClick = { dialogModif.value = true },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 5.dp),
                        text = {  Text(text = "Supprimer", fontSize = 18.sp) },
                        onClick = { showDialog.value = true },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                }
                if(showDialog.value)
                {
                    DialogDelete(idx = listTickets.indexOf(ticket) ,Tickets = listTickets ,onDismiss = {
                        showDialog.value = it
                    })
                }
                if(dialogModif.value)
                {
                    ModifElement(ticket = ticket,
                        setShowDialog = {
                            dialogModif.value = it
                        })
                }
            }
        }
    }
}