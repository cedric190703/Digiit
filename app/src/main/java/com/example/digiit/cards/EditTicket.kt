package com.example.digiit.cards

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.digiit.R
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.ui.theme.Primary
import com.example.digiit.utils.ObservableMutableState
import com.example.digiit.widgets.ColorChooser
import com.mahmoudalim.compose_rating_bar.RatingBarView
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.listItemsSingleChoice
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import es.dmoral.toasty.Toasty
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun EditTicket(ticket: Ticket, setShowDialog: (Boolean) -> Unit, setView: (Boolean) -> Unit, edit: Boolean = true){
    val ctx = LocalContext.current

    val ratingVal = ObservableMutableState(ticket.rating.toInt()) { ticket.rating = it.toFloat() }

    val colorText = ObservableMutableState(ticket.colorText) { ticket.colorTag  = it }
    val colorIcon = ObservableMutableState(ticket.colorIcon) { ticket.colorIcon = it }
    val colorTag  = ObservableMutableState(ticket.colorTag)  { ticket.colorTag  = it }

    var title by remember { mutableStateOf(ticket.title) }
    var price by remember { mutableStateOf(ticket.price) }
    var tag by remember{ mutableStateOf(ticket.tag)}
    var rating by remember { mutableStateOf(ticket.rating.toInt())}
    var comment by remember { mutableStateOf(ticket.comment) }
    var type by remember { mutableStateOf(ticket.type) }
    var date by remember { mutableStateOf(ticket.date) }

    val typeState = rememberMaterialDialogState()
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    Dialog(
        onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = Color.White
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(
                    rememberScrollState(),
                    enabled = true
                )
            ) {
                Image(
                    bitmap = ticket.getImageBitmapOrDefault(),
                    contentDescription = "photo taken",
                    modifier = Modifier
                        .padding(17.dp)
                        .size(350.dp)
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Titre") },
                    placeholder = { "Titre du ticket" }
                )

                Spacer(modifier = Modifier.padding(13.dp))
                OutlinedTextField(
                    value = price.toString(),
                    onValueChange = { price = it.toFloat() },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Prix") },
                    placeholder = { "Prix du ticket" }
                )

                Spacer(modifier = Modifier.padding(18.dp))
                Row() {
                    OutlinedTextField(
                        value = tag,
                        onValueChange = { tag = it },
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
                    Text(text = type.title, modifier = Modifier.padding(13.dp))
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
                        type = TradeKinds.findByTitle(items[it])
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
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
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
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = date.format(DateTimeFormatter.ofPattern("HH:mm")),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
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
                        date = date.withDayOfYear(it.dayOfYear).withYear(it.year)
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
                        date = date.withHour(it.hour).withMinute(it.minute).withSecond(it.second)
                    }
                }

                Spacer(modifier = Modifier.padding(13.dp))
                Text(text = "Noter ce ticket", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                RatingBarView(
                    rating = ratingVal,
                    isRatingEditable = true,
                    isViewAnimated = true,
                    ratedStarsColor = MaterialTheme.colors.primary,
                    starIcon = painterResource(id = R.drawable.full_star),
                    unRatedStarsColor = Color.LightGray,
                    starsPadding = 12.dp
                )

                Spacer(modifier = Modifier.padding(13.dp))
                OutlinedTextField(
                    value = comment,
                    modifier = Modifier.height(95.dp),
                    onValueChange = { comment = it },
                    label = { Text(text = "Commentaire") },
                    placeholder = { "Commentaire du ticket" }
                )

                Spacer(modifier = Modifier.padding(13.dp))
                ColorChooser("Couleur du text", colorText)

                Spacer(modifier = Modifier.padding(13.dp))
                ColorChooser("Couleur de l'icone", colorIcon)

                Spacer(modifier = Modifier.padding(13.dp))
                ColorChooser("Couleur du tag", colorTag)

                Spacer(modifier = Modifier.padding(13.dp))
                Row() {
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 2.dp),
                        text = {  Text(text = if (edit) "Modifier" else "Ajouter", fontSize = 18.sp) },
                        onClick = {
                            ticket.title = title
                            ticket.type = type
                            ticket.price = price
                            ticket.tag = tag
                            ticket.date = date
                            ticket.comment = comment
                            ticket.rating = rating.toFloat()
                            ticket.colorIcon = colorIcon.value
                            ticket.colorTag = colorTag.value
                            ticket.colorText = colorText.value

                            if(ticket.isValid()) {
                                ticket.save { error ->
                                    if (error == null) {
                                        Toasty.success(ctx, "Le ticket a bien été modifié", Toast.LENGTH_SHORT, true).show()
                                        setShowDialog(false)
                                        setView(false)
                                    } else {
                                        Toasty.error(ctx, "Erreur de sauvegarde du ticket", Toast.LENGTH_SHORT, true).show()
                                    }
                                }
                            } else {
                                Toasty.error(ctx, "Des champs n'ont pas été remplis correctement", Toast.LENGTH_SHORT, true).show()
                            }
                        },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .padding(vertical = 18.dp, horizontal = 2.dp),
                        text = {  Text(text = "Fermer", fontSize = 18.sp) },
                        onClick = { setShowDialog(false) },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}