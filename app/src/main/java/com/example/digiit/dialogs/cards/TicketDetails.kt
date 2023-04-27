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
import com.example.digiit.R
import com.example.digiit.cards.DeleteCard
import com.example.digiit.cards.EditCard
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.photos.TypeScreen
import com.example.digiit.widgets.AsyncImage
import com.mahmoudalim.compose_rating_bar.RatingBarView
import java.time.format.DateTimeFormatter


@Composable
fun TicketDetails(setState: (Boolean) -> Unit, ticket: Ticket, auth: ApplicationData) {
    val ratingVal = remember { mutableStateOf(ticket.rating.toInt()) }
    val bigMode = remember { mutableStateOf(false) }
    val deleteDialog = remember { mutableStateOf(false) }
    val editDialog = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { setState(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = ticket.colorTag) {
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
                        ticket.loadImage {
                            callback(ticket.getImageBitmapOrDefault())
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = "${ticket.price}$",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = ticket.title,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                if (bigMode.value) {
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "#${ticket.tag}",
                        color = Color.White,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.padding(6.dp))
                ExtendedFloatingActionButton(
                    modifier = Modifier.height(65.dp),
                    text = {  Text(text = ticket.type.title,
                        fontSize = 17.sp, color = Color.White) },
                    backgroundColor = Color.Transparent,
                    onClick = {  },
                    icon = {
                        Icon(painter = painterResource(ticket.type.icon),
                            "ticket type logo",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(35.dp),
                            tint = Color.White)
                    }
                )

                Spacer(modifier = Modifier.padding(6.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                            text = ticket.date.format(DateTimeFormatter.ofPattern("")),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                            text = ticket.date.format(DateTimeFormatter.ofPattern("")),
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
                        text = ticket.comment,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

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
                        onClick = { editDialog.value = true },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 5.dp),
                        text = {  Text(text = "Supprimer", fontSize = 18.sp) },
                        onClick = { deleteDialog.value = true },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                }

                if(deleteDialog.value)
                {
                    DeleteCard(ticket, onDismiss = { deleted ->
                        deleteDialog.value = false
                        setState(!deleted)
                    }, auth = auth)
                }

                if(editDialog.value)
                {
                    EditCard(
                        ticket,
                        setShowDialog = {
                            editDialog.value = it
                        },
                        setView = {
                            setState(it)
                        },
                        edit = true,
                        typeScreen = TypeScreen.Ticket
                    )
                }
            }
        }
    }
}
