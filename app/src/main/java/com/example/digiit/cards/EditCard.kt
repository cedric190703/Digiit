package com.example.digiit.cards

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.digiit.R
import com.example.digiit.data.CommercialType
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.card.Card
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.data.wallet.Wallet
import com.example.digiit.utils.ObservableMutableState
import com.example.digiit.widgets.AsyncImage
import com.example.digiit.widgets.ColorChooser
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
fun EditCard(
    card: Card,
    setShowDialog: (Boolean) -> Unit,
    setView: (Boolean) -> Unit,
    edit: Boolean = true
) {
    val ctx = LocalContext.current

    val ticket = card as? Ticket
    val wallet = card as? Wallet

    val rating = if(ticket != null) ObservableMutableState(ticket.rating.toInt()) { ticket.rating = it.toFloat() } else null

    val colorIcon = ObservableMutableState(card.colorIcon) { card.colorIcon = it }
    val colorTag  = ObservableMutableState(card.colorTag)  { card.colorTag  = it }

    var title by remember { mutableStateOf(card.title) }
    var price by remember { mutableStateOf(card.price) }
    var tag by remember{ mutableStateOf(card.tag)}
    var comment by remember { mutableStateOf(card.comment) }
    var type by remember { mutableStateOf(card.type) }
    var date by remember { mutableStateOf(card.date) }

    val expiry = if(wallet != null) remember { mutableStateOf(wallet.expiryDate) } else null

    val typeState = rememberMaterialDialogState()
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    val typeStateDocs = if (wallet != null) rememberMaterialDialogState() else null
    val walletType = if (wallet != null) remember { mutableStateOf(wallet.walletType) } else null

    val expiryDateDialogState = if (wallet != null) rememberMaterialDialogState() else null
    val expiryTimeDialogState = if (wallet != null) rememberMaterialDialogState() else null


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
                AsyncImage(modifier = Modifier
                        .padding(17.dp)
                        .size(350.dp)
                ) { callback ->
                    card.loadImage {
                        callback(card.getImageBitmapOrDefault())
                    }
                }
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Titre") },
                    placeholder = { Text(text = "Titre du ticket") }
                )

                Spacer(modifier = Modifier.padding(13.dp))
                OutlinedTextField(
                    value = if (price == 0.0f) "" else price.toString(),
                    onValueChange = {
                        try {
                            price = it.toFloat()
                        } catch (_: java.lang.NumberFormatException) {}
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Prix") },
                    placeholder = { Text(text = "Prix du ticket") }
                )

                Spacer(modifier = Modifier.padding(18.dp))
                Row() {
                    OutlinedTextField(
                        value = tag,
                        onValueChange = { tag = it },
                        label = { Text(text = "Tag") },
                        placeholder = { Text(text = "Tag du ticket") }
                    )
                }

                Spacer(modifier = Modifier.padding(12.dp))
                Button(modifier = Modifier
                    .width(250.dp)
                    .padding(4.dp),
                    onClick = { }
                ) {
                    Text(text = "Type de produit :",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold)
                }

                Card(
                    elevation = 10.dp,
                    border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                    modifier = Modifier.padding(10.dp)
                        .clickable { typeState.show() }
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
                        initialSelection = type.ordinal
                    ) {
                        type = TradeKinds.findByTitle(items[it])
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))
                Button(onClick = { }) {
                    Text(text = "Sélectionner une date :", fontSize = 18.sp)
                }

                Card(
                    elevation = 10.dp,
                    border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                    modifier = Modifier.padding(12.dp)
                        .clickable { dateDialogState.show() }
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { }) {
                    Text(text = "Sélectionner une heure :", fontSize = 18.sp)
                }

                Card(
                    elevation = 10.dp,
                    border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                    modifier = Modifier.padding(12.dp)
                        .clickable { timeDialogState.show() }
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
                        title = "Sélectionner une date :",
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
                        title = "Sélectionner l'heure :"
                    ) {
                        date = date.withHour(it.hour).withMinute(it.minute).withSecond(it.second)
                    }
                }

                if (ticket != null) {
                    Spacer(modifier = Modifier.padding(13.dp))
                    Text(text = "Noter ce ticket", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    RatingBarView(
                        rating = rating!!,
                        isRatingEditable = true,
                        isViewAnimated = true,
                        ratedStarsColor = MaterialTheme.colors.primary,
                        starIcon = painterResource(id = R.drawable.full_star),
                        unRatedStarsColor = Color.LightGray,
                        starsPadding = 12.dp
                    )
                }

                Spacer(modifier = Modifier.padding(13.dp))
                OutlinedTextField(
                    value = comment,
                    modifier = Modifier.height(95.dp),
                    onValueChange = { comment = it },
                    label = { Text(text = "Commentaire") },
                    placeholder = { Text(text = "Commentaire du ticket") }
                )

                if(wallet != null) {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(modifier = Modifier.padding(12.dp),
                        onClick = { }) {
                        Text(text = "Sélectionner une date d'expiration :", fontSize = 18.sp)
                    }

                    Card(
                        elevation = 10.dp,
                        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                        modifier = Modifier.padding(12.dp)
                            .clickable { expiryDateDialogState!!.show() }
                    ) {
                        Text(modifier = Modifier.padding(12.dp), text = expiry!!.value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(modifier = Modifier.padding(18.dp),
                        onClick = { }) {
                        Text(text = "Sélectionner une heure d'expiration :", fontSize = 18.sp)
                    }

                    Card(
                        elevation = 10.dp,
                        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                        modifier = Modifier.padding(12.dp)
                            .clickable { expiryTimeDialogState!!.show() }
                    ) {
                        Text(modifier = Modifier.padding(12.dp),text = expiry!!.value.format(DateTimeFormatter.ofPattern("HH:mm")), fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    }

                    MaterialDialog(
                        dialogState = expiryDateDialogState!!,
                        buttons = {
                            positiveButton(text = "Ok")
                            negativeButton(text = "Fermer")
                        }
                    ) {
                        datepicker(
                            initialDate = LocalDate.now(),
                            title = "Sélectionner une date d'expiration :",
                        ) {
                            expiry!!.value = expiry.value.withDayOfYear(it.dayOfYear).withYear(it.year)
                        }
                    }

                    MaterialDialog(
                        dialogState = expiryTimeDialogState!!,
                        buttons = {
                            positiveButton(text = "Ok")
                            negativeButton(text = "Fermer")
                        }
                    ) {
                        timepicker(
                            initialTime = LocalTime.NOON,
                            title = "Sélectionner l'heure d'expiration :"
                        ) {
                            expiry!!.value = expiry.value.withHour(it.hour).withMinute(it.minute).withSecond(it.second)
                        }
                    }

                    Spacer(modifier = Modifier.padding(12.dp))
                    Button(modifier = Modifier
                        .width(250.dp)
                        .padding(4.dp), onClick = { }) {
                        Text(text = "Sélectionner un type de document :",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold)
                    }

                    Card(
                        elevation = 10.dp,
                        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                        modifier = Modifier.padding(10.dp)
                            .clickable { typeStateDocs!!.show() }
                    ) {
                        Text(text = walletType!!.value.title, modifier = Modifier.padding(13.dp))
                    }

                    MaterialDialog(dialogState = typeStateDocs!!, buttons = {
                        positiveButton(text = "Ok")
                        negativeButton(text = "Fermer")
                    }) {
                        val items = CommercialType.values().map { doc -> doc.title }
                        listItemsSingleChoice(
                            list = items,
                            initialSelection = walletType!!.value.ordinal
                        ) {
                            walletType.value = CommercialType.findByTitle(items[it])
                        }
                    }
                }

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
                            card.title = title
                            card.type = type
                            card.price = price
                            card.tag = tag
                            card.date = date
                            card.comment = comment
                            card.colorIcon = colorIcon.value
                            card.colorTag = colorTag.value

                            if (ticket != null) {
                                ticket.rating = rating!!.value.toFloat()
                                if (ticket.isValid()) {
                                    ticket.save { error ->
                                        if (error == null) {
                                            ticket.saveImage { err ->
                                                if (err == null) {
                                                    Toasty.success(
                                                        ctx,
                                                        "Le ticket a bien été modifié",
                                                        Toast.LENGTH_SHORT,
                                                        true
                                                    ).show()
                                                    setShowDialog(false)
                                                    setView(false)
                                                } else {
                                                    Toasty.error(
                                                        ctx,
                                                        "Erreur de sauvegarde de l'image du ticket",
                                                        Toast.LENGTH_SHORT,
                                                        true
                                                    ).show()
                                                }
                                            }
                                        } else {
                                            Toasty.error(
                                                ctx,
                                                "Erreur de sauvegarde du ticket",
                                                Toast.LENGTH_SHORT,
                                                true
                                            ).show()
                                        }
                                    }
                                } else {
                                    Toasty.error(
                                        ctx,
                                        "Des champs n'ont pas été remplis correctement",
                                        Toast.LENGTH_SHORT,
                                        true
                                    ).show()
                                }
                            } else if(wallet != null) {
                                wallet.expiryDate = expiry!!.value
                                wallet.walletType = walletType!!.value
                                if (wallet.isValid()) {
                                    wallet.save { error ->
                                        if (error == null) {
                                            wallet.saveImage { err ->
                                                if (err == null) {
                                                    Toasty.success(
                                                        ctx,
                                                        "Le porte feuilles a bien été modifié",
                                                        Toast.LENGTH_SHORT,
                                                        true
                                                    ).show()
                                                    setShowDialog(false)
                                                    setView(false)
                                                } else {
                                                    Toasty.error(
                                                        ctx,
                                                        "Erreur de sauvegarde de l'image du feuilles",
                                                        Toast.LENGTH_SHORT,
                                                        true
                                                    ).show()
                                                }
                                            }
                                        } else {
                                            Toasty.error(
                                                ctx,
                                                "Erreur de sauvegarde du feuilles",
                                                Toast.LENGTH_SHORT,
                                                true
                                            ).show()
                                        }
                                    }
                                } else {
                                    Toasty.error(
                                        ctx,
                                        "Des champs n'ont pas été remplis correctement",
                                        Toast.LENGTH_SHORT,
                                        true
                                    ).show()
                                }
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