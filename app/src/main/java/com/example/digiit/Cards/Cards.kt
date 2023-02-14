package com.example.digiit.Cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.Home.ticket
import com.example.digiit.R
import com.mahmoudalim.compose_rating_bar.RatingBarView
import java.util.*

@Composable
fun ticketsCard(
    ticket: ticket
) {
    val ratingVal = remember {mutableStateOf(ticket.rating)}
    val paddingModifier = Modifier.padding(10.dp)
    Card(
        elevation = 10.dp,
        border = BorderStroke(2.dp, ticket.colorIcon),
        modifier = paddingModifier
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
                Column(modifier = Modifier
                    .width(45.dp),
                    verticalArrangement = Arrangement.Center) {
                    OutlinedButton(onClick = {  },
                        modifier= Modifier.size(50.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(modifier = Modifier.size(35.dp),
                            painter = painterResource(id = R.drawable.more_vert),
                            contentDescription = "more vert icon")
                    }
                }
            }
            RatingBarView(
                rating = ratingVal,
                isRatingEditable = false,
                isViewAnimated = false,
                ratedStarsColor = MaterialTheme.colors.primary,
                starIcon = painterResource(id = R.drawable.full_star),
                unRatedStarsColor = Color.LightGray,
                starsPadding = 10.dp
            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}