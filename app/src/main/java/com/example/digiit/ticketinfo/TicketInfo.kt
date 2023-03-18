package com.example.digiit.ticketinfo

import android.graphics.Bitmap
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.graphics.asImageBitmap
import com.example.digiit.data.user.RemoteUser
import com.example.digiit.data.user.User
import com.example.digiit.ui.theme.Primary
import com.vanpra.composematerialdialogs.color.colorChooser

@Composable
fun DialogTicketInfo(
    user: User?, bitmap: Bitmap,
    setShowDialogPhoto: (Boolean) -> Unit) {
    val items = TradeKinds.values().map { tag -> tag.title }
    val titrelVal = remember { mutableStateOf("") }
    val prixVal = remember {mutableStateOf("")}
    val idxTag = remember{ mutableStateOf(0)}
    val tagVal = remember { mutableStateOf(items[idxTag.value]) }
    val rating = remember { mutableStateOf(5)}
    val colorTextIdx = remember{ mutableStateOf(18) }
    val colorText = remember { mutableStateOf(Primary[colorTextIdx.value]) }
    val colorIconIdx = remember{ mutableStateOf(5) }
    val colorIcon = remember { mutableStateOf(Primary[colorIconIdx.value]) }
    val colorTagIdx = remember{ mutableStateOf(5) }
    val colorTag = remember { mutableStateOf(Primary[colorTagIdx.value]) }
    val comment = remember { mutableStateOf("") }
    var typeVal by remember {
        mutableStateOf(TradeKinds.Food.title) }
    val typeState = rememberMaterialDialogState()
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
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
    val colorTextDialog = rememberMaterialDialogState()
    val colorIconDialog = rememberMaterialDialogState()
    val colorTagDialog = rememberMaterialDialogState()
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
                    bitmap = bitmap.asImageBitmap(),
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
                    listItemsSingleChoice(
                        list = items,
                        initialSelection = idxTag.value
                    ) {
                        typeVal = items[it]
                        idxTag.value = items.indexOf(typeVal)
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
                        initialDate = pickedDate,
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
                        initialTime = pickedTime,
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
                    label = { Text(text = "Commentaire") },
                    placeholder = { "Commentaire du ticket" }
                )
                Spacer(modifier = Modifier.padding(13.dp))
                Button(modifier = Modifier.padding(horizontal = 12.dp) ,onClick = {
                    colorTextDialog.show()
                }) {
                    Text(text = "Sélectionner une couleur pour le texte:", fontSize = 18.sp)
                }
                MaterialDialog(
                    dialogState = colorTextDialog,
                    buttons = {
                        positiveButton(text = "Ok")
                        negativeButton(text = "Fermer")
                    }
                ) {
                    colorChooser(colors = Primary, initialSelection = colorTextIdx.value) {
                        colorText.value = it
                        colorTextIdx.value = Primary.indexOf(colorText.value)
                    }
                }
                Spacer(modifier = Modifier.padding(13.dp))
                Button(modifier = Modifier.padding(horizontal = 12.dp) ,onClick = {
                    colorIconDialog.show()
                }) {
                    Text(text = "Sélectionner une couleur pour l'icone:", fontSize = 18.sp)
                }
                MaterialDialog(
                    dialogState = colorIconDialog,
                    buttons = {
                        positiveButton(text = "Ok")
                        negativeButton(text = "Fermer")
                    }
                ) {
                    colorChooser(colors = Primary, initialSelection = colorIconIdx.value) {
                        colorIcon.value = it
                        colorIconIdx.value = Primary.indexOf(colorIcon.value)
                    }
                }
                Spacer(modifier = Modifier.padding(13.dp))
                Button(modifier = Modifier.padding(horizontal = 12.dp) ,onClick = {
                    colorTagDialog.show()
                }) {
                    Text(text = "Sélectionner une couleur pour le tag:", fontSize = 18.sp)
                }
                MaterialDialog(
                    dialogState = colorTagDialog,
                    buttons = {
                        positiveButton(text = "Ok")
                        negativeButton(text = "Fermer")
                    }
                ) {
                    colorChooser(colors = Primary, initialSelection = colorTagIdx.value) {
                        colorTag.value = it
                        colorTagIdx.value = Primary.indexOf(colorTag.value)
                    }
                }
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
                                colorText.value, colorIcon.value, colorTag.value, rating.value, comment.value, bitmap, user)
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
