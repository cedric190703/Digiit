package com.example.digiit.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.R
import com.example.digiit.data.UserProvider
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.data.wallet.Wallet
import com.example.digiit.ui.theme.Grad
import com.mahmoudalim.compose_rating_bar.RatingBarView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TicketCard(ticket: Ticket, auth: UserProvider) {
    val ratingVal = remember { mutableStateOf(ticket.rating.toInt()) }
    val showDialog = remember { mutableStateOf(false) }
    Card(
        elevation = 0.dp,
        //  border = BorderStroke(1.dp, ticket.colorIcon),
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { showDialog.value = true }
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        ticket.colorTag,
                        Grad.getOrDefault(ticket.colorTag, Color.Black),
                    )
                )

            )
    ) {
        Row(
            modifier = Modifier
                //.fillMaxWidth()
                .fillMaxSize()
                .padding(0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(5.dp)
                    .width(84.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                // new row with icon and title inside
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                        // ICON
                        OutlinedButton(onClick = { },
                            modifier= Modifier.size(50.dp),
                            shape = CircleShape,
                            border= BorderStroke(2.dp, ticket.colorIcon),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor =  ticket.colorIcon)
                        ) {
                            Icon(modifier = Modifier.size(38.dp),
                                painter = painterResource(ticket.type.icon),
                                contentDescription = "icon description")
                        }
                    // Espace
                    Spacer(modifier = Modifier.width(10.dp))

                    // TITLE
                    Text(
                        text = ticket.title,
                        modifier = Modifier.padding(vertical = 1.dp),
                        fontSize = 24.sp,
                        //fontWeight = FontWeight.Bold,
                        color =  Color.White)
                }
                // new row with raiting inside
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                ){
                    // RATING
                    RatingBarView(
                        rating = ratingVal,
                        isRatingEditable = false,
                        isViewAnimated = false,
                        ratedStarsColor = Color.White,
                        starIcon = painterResource(id = R.drawable.full_star),
                        unRatedStarsColor = Color.LightGray,
                        starsPadding = 6.dp,
                        starSize = 35.dp
                    )
                }

                // new row with date inside
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // DATE
                    Text(text = ticket.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm")),
                        color = Color.White,
                        fontSize = 16.sp)
                }
                // new row with tags inside
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // TAG
                    Tag(ticket.tag)
                }
            }
            // new column with price inside
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp)
                    .width(84.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                // PRICE
                Text(
                    text = "${ticket.price}$",
                    modifier = Modifier.padding(vertical = 12.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)
            }
        }
    }
    if(showDialog.value)
    {
        CardViewSmall(setState = { showDialog.value = it }, ticket, auth)
    }
}

@Composable
fun WalletsCard(
    wallet: Wallet
) {
    val showDialog = remember { mutableStateOf(false)}
    Card(
        elevation = 0.dp,
        //border = BorderStroke(1.dp, wallet.colorIcon),
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { showDialog.value = true }
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = if (LocalDateTime.now() > wallet.expiryDate)
                        listOf(
                            Color(0xFFEF5350),
                            Color(0xFFE57373),
                        ) as List<Color>
                    else
                        if (wallet.used)
                            listOf(
                                Color(0xFF9E9797),
                                Color(0xFF5A5656),
                            ) as List<Color>
                        else
                            listOf(
                                Color(0xFF80CBC4),
                                Color(0xFFC5E1A5),
                            ) as List<Color>
                )

            )
    ) {
        //parent column
        Column() {


            // Main Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // new column with Wallet kind inside
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(5.dp)
                        .width(84.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    // ROW PRICE
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // PRICE
                        Text(
                            text = "$${wallet.price}",
                            // pading but only top and left
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 5.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    // ROW DATE
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // DATE
                        Text(
                            text = "Expire le \n" + wallet.expiryDate.format(
                                DateTimeFormatter.ofPattern(
                                    "dd/MM/yyyy à HH:mm"
                                )
                            ),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(7.0f)
                        .padding(5.dp)
                        .width(84.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        // WALLET KIND
                        Text(
                            text = wallet.walletType.title,
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontSize = 22.sp,
                            //fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        // ICON
                        Icon(
                            painter = painterResource(id = wallet.walletType.icon),
                            contentDescription = null,
                            tint = wallet.colorIcon,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(5.dp)
                        )
                    }
                    // new row with title inside
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(7.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.End
                    ) {
                        // Tags
                        Tag(wallet.tag)
                        /*Text(
                            text = "#${wallet.tag}",
                            modifier = Modifier.padding(vertical = 1.dp),
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )*/
                    }
                }


            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // WALLET TITLE
                Text(
                    text = wallet.title,
                    modifier = Modifier.padding(vertical = 1.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

//TODO : couleur