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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.home.ticket
import com.example.digiit.R
import com.example.digiit.home.wallet
import com.mahmoudalim.compose_rating_bar.RatingBarView

@Composable
fun TicketsCard(
    ticket: ticket
) {
    val ratingVal = remember {mutableStateOf(ticket.rating)}
    val showDialog = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        border = BorderStroke(1.dp, ticket.colorIcon),
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
                        border= BorderStroke(2.dp, ticket.colorIcon),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor =  ticket.colorIcon)
                    ) {
                        Icon(modifier = Modifier.size(38.dp),
                            painter = painterResource(ticket.typeCommerce.icon),
                            contentDescription = "icon description")
                    }
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "#${ticket.tag}",
                        color = ticket.colorTag,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.padding(1.dp))
                Column(modifier = Modifier
                    .width(111.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = ticket.titre,
                        modifier = Modifier.padding(vertical = 12.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ticket.colorText)
                    Text(text = ticket.dateDate)
                    Text(text = ticket.dateTime)
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column(modifier = Modifier
                    .width(83.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = "${ticket.prix}$",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = ticket.colorText)
                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
            if(showDialog.value)
            {
                CardViewSmall(setState = { showDialog.value = it }, ticket)
            }
            RatingBarView(
                rating = ratingVal,
                isRatingEditable = false,
                isViewAnimated = false,
                ratedStarsColor = MaterialTheme.colors.primary,
                starIcon = painterResource(id = R.drawable.full_star),
                unRatedStarsColor = Color.LightGray,
                starsPadding = 6.dp,
                starSize = 35.dp
            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun WalletsCard(
    wallet: wallet
) {
    val ratingVal = remember {mutableStateOf(wallet.rating)}
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
                            painter = painterResource(wallet.typeCommerce.icon),
                            contentDescription = "icon description")
                    }
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "#${wallet.tag}",
                        color = wallet.colorTag,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.padding(1.dp))
                Column(modifier = Modifier
                    .width(111.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = wallet.titre,
                        modifier = Modifier.padding(vertical = 12.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = wallet.colorText)
                    Text(text = wallet.dateDate)
                    Text(text = wallet.dateTime)
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column(modifier = Modifier
                    .width(83.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = "${wallet.prix}$",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = wallet.colorText)
                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
            if(showDialog.value)
            {
                // CardViewSmall(setState = { showDialog.value = it }, wallet) for Wallets
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
                    text = "${wallet.walletType.title}",
                    color = wallet.colorText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            }
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = "${wallet.expiryDate}",
                color = wallet.colorText,
                fontSize = 20.sp)
            RatingBarView(
                rating = ratingVal,
                isRatingEditable = false,
                isViewAnimated = false,
                ratedStarsColor = MaterialTheme.colors.primary,
                starIcon = painterResource(id = R.drawable.full_star),
                unRatedStarsColor = Color.LightGray,
                starsPadding = 6.dp,
                starSize = 35.dp
            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}