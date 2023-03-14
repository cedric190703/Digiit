package com.example.digiit.ticketinfo

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.digiit.data.TradeKinds
import com.example.digiit.R
import com.example.digiit.actiononelement.createTicket
import com.mahmoudalim.compose_rating_bar.RatingBarView
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.listItemsSingleChoice
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import es.dmoral.toasty.Toasty
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun DialogTicketInfo(painter: Painter, setShowDialogPhoto: (Boolean) -> Unit) {
    var titrelVal = remember { mutableStateOf("") }
    var prixVal = remember {mutableStateOf("")}
    var tagVal = remember { mutableStateOf("") }
    var rating = remember { mutableStateOf(5)}
    val comment = remember { mutableStateOf("") }
    var typeVal by remember {
        mutableStateOf(TradeKinds.Food.title) }
    var typeState = rememberMaterialDialogState()
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    val ctx = LocalContext.current
    Dialog(
        onDismissRequest = { setShowDialogPhoto(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = Color.White
        ) {
            Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.verticalScroll(
                        rememberScrollState(),
                        enabled = true
                    )) {
                Image(
                    painter = painter,
                    contentDescription = "photo taken",
                    modifier = Modifier
                        .padding(17.dp)
                        .size(350.dp))
                OutlinedTextField(
                    value = titrelVal.value,
                    onValueChange = { titrelVal.value = it },
                    label = { Text(text = "Titre") },
                    placeholder = { "Titre du ticket" }
                )
                Spacer(modifier = Modifier.padding(13.dp))
                OutlinedTextField(
                    value = prixVal.value,
                    onValueChange = { prixVal.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Prix") },
                    placeholder = { "Prix du ticket" }
                )
                Spacer(modifier = Modifier.padding(18.dp))
                Row() {
                    OutlinedTextField(
                        value = tagVal.value,
                        onValueChange = { tagVal.value = it },
                        label = { Text(text = "Tag") },
                        placeholder = { "Tag du ticket" }
                    )
                }
                Spacer(modifier = Modifier.padding(12.dp))
                Button(modifier = Modifier
                    .width(250.dp)
                    .padding(4.dp), onClick = {
                    typeState.show()
                }) {
                    Text(text = "Sélectionner un type de produit", fontSize = 18.sp)
                }
                Card(
                    elevation = 10.dp,
                    border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = typeVal, modifier = Modifier.padding(13.dp))
                }
                MaterialDialog(dialogState = typeState, buttons = {
                    positiveButton(text = "Ok")
                    negativeButton(text = "Fermer")
                }) {
                    val items = TradeKinds.values().map { tag -> tag.title }
                    listItemsSingleChoice(
                        list = items,
                        disabledIndices = setOf(1)
                    ) {
                        typeVal = items[it]
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Button(onClick = {
                    dateDialogState.show()
                }) {
                    Text(text = "Sélectionner une date", fontSize = 18.sp)
                }
                Card(
                    elevation = 10.dp,
                    border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(modifier = Modifier.padding(12.dp),text = formattedDate, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    timeDialogState.show()
                }) {
                    Text(text = "Sélectionner une heure", fontSize = 18.sp)
                }
                Card(
                    elevation = 10.dp,
                    border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(modifier = Modifier.padding(12.dp),text = formattedTime, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                }
                MaterialDialog(
                    dialogState = dateDialogState,
                    buttons = {
                        positiveButton(text = "Ok")
                        negativeButton(text = "Fermer")
                    }
                ) {
                    datepicker(
                        initialDate = LocalDate.now(),
                        title = "Sélectionner une date",
                    ) {
                        pickedDate = it
                    }
                }
                MaterialDialog(
                    dialogState = timeDialogState,
                    buttons = {
                        positiveButton(text = "Ok")
                        negativeButton(text = "Fermer")
                    }
                ) {
                    timepicker(
                        initialTime = LocalTime.NOON,
                        title = "Sélectionner l'heure"
                    ) {
                        pickedTime = it
                    }
                }
                Spacer(modifier = Modifier.padding(13.dp))
                Text(text = "Noter ce ticket", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                RatingBarView(
                    rating = rating,
                    isRatingEditable = true,
                    isViewAnimated = true,
                    ratedStarsColor = MaterialTheme.colors.primary,
                    starIcon = painterResource(id = R.drawable.full_star),
                    unRatedStarsColor = Color.LightGray,
                    starsPadding = 12.dp
                )
                Spacer(modifier = Modifier.padding(13.dp))
                OutlinedTextField(
                    value = comment.value,
                    modifier = Modifier.height(95.dp),
                    onValueChange = { comment.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Commentaire") },
                    placeholder = { "Commentaire du ticket" }
                )
                Spacer(modifier = Modifier.padding(13.dp))
                Row() {
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 5.dp),
                        text = {  Text(text = "Ajouter élement", fontSize = 18.sp) },
                        onClick = { if(typeVal != "" && titrelVal.value != "") {
                            createTicket(typeVal, tagVal.value, titrelVal.value,
                                prixVal.value.toInt(), pickedTime.toString(), pickedDate.toString(),
                                Color.Red, Color.Blue, Color.Black, rating.value, comment.value, painter)
                            setShowDialogPhoto(false)
                            Toasty.success(ctx, "Le ticket a bien été ajouté", Toast.LENGTH_SHORT, true).show()
                        } else {
                            Toasty.error(ctx, "Des champs n'ont pas été remplis", Toast.LENGTH_SHORT, true).show()
                        } },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 5.dp),
                        text = {  Text(text = "Fermer", fontSize = 18.sp) },
                        onClick = { setShowDialogPhoto(false) },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}