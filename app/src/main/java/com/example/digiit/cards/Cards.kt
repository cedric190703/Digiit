package com.example.digiit.cards

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.R
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.data.wallet.Wallet
import com.mahmoudalim.compose_rating_bar.RatingBarView
import java.time.format.DateTimeFormatter


@Composable
fun TicketCard(
    ticket: Ticket
) {
    val ratingVal = remember { mutableStateOf(ticket.rating.toInt()) }
    val showDialog = remember { mutableStateOf(false) }
    Card(
        elevation = 5.dp,
        border = BorderStroke(1.dp, ticket.colorIcon),
        backgroundColor = if(ticket.title == "hello") Color(0xFFF1A45F) else Color(0xFFC5E1A5),
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { showDialog.value = true }
            .border(
                width = 3.dp,
                color = if(ticket.title == "hello") Color(0xFFF1A45F) else Color(0xFFC5E1A5), //ticket.colorTag,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.size(50.dp),
                        shape = CircleShape,
                        border = BorderStroke(2.dp, ticket.colorIcon),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ticket.colorIcon)
                    ) {
                        // ICON
                        OutlinedButton(onClick = {  },
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
                    }
                    // Espace
                    Spacer(modifier = Modifier.width(10.dp))

                    // TITLE
                    Text(
                        text = ticket.title,
                        modifier = Modifier.padding(vertical = 12.dp),
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
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "#${ticket.tag}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 16.sp)
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
        CardViewSmall(setState = { showDialog.value = it }, ticket)
    }
}

@Composable
fun WalletsCard(
    wallet: Wallet
) {
    val showDialog = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        border = BorderStroke(1.dp, wallet.colorIcon),
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { showDialog.value = true }
            .border(
                width = 3.dp,
                color = Color.Red,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically){
                Column(modifier = Modifier
                    .padding(5.dp)
                    .width(84.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(onClick = {  },
                        modifier= Modifier.size(50.dp),
                        shape = CircleShape,
                        border= BorderStroke(2.dp, wallet.colorIcon),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor =  wallet.colorIcon)
                    ) {
                        Icon(modifier = Modifier.size(38.dp),
                            painter = painterResource(wallet.walletType.icon),
                            contentDescription = "icon description")
                    }
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "#${wallet.tag}",
                        color = wallet.colorTag,
                        fontSize = 20.sp)
                        //fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.padding(1.dp))
                Column(modifier = Modifier
                    .width(111.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = wallet.title,
                        modifier = Modifier.padding(vertical = 12.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = wallet.colorText)
                    Text(text = wallet.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    Text(text = wallet.date.format(DateTimeFormatter.ofPattern("HH:mm")))
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column(modifier = Modifier
                    .width(83.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = "${wallet.price}$",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = wallet.colorText)
                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
            if(showDialog.value)
            {
                CardViewSmallWallet(setState = { showDialog.value = it }, wallet)
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                OutlinedButton(onClick = {  },
                    modifier= Modifier.size(50.dp),
                    shape = CircleShape,
                    border= BorderStroke(2.dp, wallet.colorIcon),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor =  wallet.colorIcon)
                ) {
                    Icon(modifier = Modifier.size(38.dp),
                        painter = painterResource(wallet.walletType.icon),
                        contentDescription = "icon description")
                }
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = wallet.walletType.title,
                    color = wallet.colorText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            }
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = "${wallet.expiryDate}",
                color = wallet.colorText,
                fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}