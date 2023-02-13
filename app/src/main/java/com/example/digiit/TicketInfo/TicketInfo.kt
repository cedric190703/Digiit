package com.example.digiit.TicketInfo

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.digiit.Cards.listOfTags
import com.example.digiit.Cards.tags
import com.example.digiit.Home.ticket
import com.example.digiit.addTicket
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.listItemsSingleChoice
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun dialogTicketInfo(setShowDialogPhoto: (Boolean) -> Unit,
                     painter: Painter) {
    var titrelVal = remember {mutableStateOf(TextFieldValue(""))}
    var prixVal = remember {mutableStateOf(TextFieldValue(""))}
    var tagVal = remember { mutableStateOf("") }
    var typeVal by remember {
        mutableStateOf("") }
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
    Dialog(
        onDismissRequest = { setShowDialogPhoto(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = Color.White
        ) {
            Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.verticalScroll(rememberScrollState())) {
                Image(
                    painter = painter,
                    contentDescription = "photo taken",
                    modifier = Modifier
                        .padding(17.dp)
                        .size(350.dp)
                )
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
                Spacer(modifier = Modifier.padding(13.dp))
                Row() {
                    OutlinedTextField(
                        value = tagVal.value,
                        onValueChange = { tagVal.value = it },
                        label = { Text(text = "Tag") },
                        placeholder = { "Tag du ticket" }
                    )
                }
                if(typeVal != null)
                {
                    Text(text = typeVal, modifier = Modifier.padding(13.dp))
                }
                ExtendedFloatingActionButton(
                    modifier = Modifier.padding(8.dp),
                    text = {  Text(text = "Sélectionner un type de produit",
                        fontSize = 16.sp) },
                    onClick = { typeState.show() },
                    backgroundColor = MaterialTheme.colors.primary
                )
                MaterialDialog(dialogState = typeState, buttons = {
                    positiveButton(text = "Ok")
                    negativeButton(text = "Fermer")
                }) {
                    listItemsSingleChoice(
                        list = listOfTags,
                        disabledIndices = setOf(1),
                        initialSelection = 1
                    ) {
                        typeVal = listOfTags[it]
                    }
                }
                Spacer(modifier = Modifier.padding(13.dp))
                Button(onClick = {
                    dateDialogState.show()
                }) {
                    Text(text = "Sélectionner une date")
                }
                Text(text = formattedDate)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    timeDialogState.show()
                }) {
                    Text(text = "Sélectionner une heure")
                }
                Text(text = formattedTime)
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
                Row() {
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(58.dp)
                            .padding(5.dp),
                        text = {  Text(text = "Ajouter élement", fontSize = 18.sp) },
                        onClick = { setShowDialogPhoto(false)},
                        backgroundColor = MaterialTheme.colors.primary
                    )
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(58.dp)
                            .padding(5.dp),
                        text = {  Text(text = "Fermer", fontSize = 18.sp) },
                        onClick = { setShowDialogPhoto(false) },
                        backgroundColor = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}