package com.example.digiit.photos

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.digiit.cards.EditCard
import com.example.digiit.data.card.Card
import com.example.digiit.data.user.User
import com.example.digiit.widgets.LabelledCheckBox
import com.example.digiit.widgets.LottieLoadingAnimation
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

enum class TypeScreen {
    Ticket, Wallet
}

@Composable
fun SelectOption(
    setShowDialog: (Boolean) -> Unit,
    user: User?,
    typeScreen: TypeScreen){

    val enableOCR = remember { mutableStateOf(false) }

    val showEditCard = remember { mutableStateOf(false) }

    val image = remember { mutableStateOf<Bitmap?>(null) }
    val card = remember { mutableStateOf<Card?>(null) }

    val showDialogLoader = remember {
        mutableStateOf(false)
    }

    if (showDialogLoader.value) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Loading") },
            text = {
                LottieLoadingAnimation()
            },
            buttons = {}
        )
    }

    if (showEditCard.value && card.value != null) {
        EditCard(card.value!!, {
            setShowDialog(false)
        }, {
            setShowDialog(false)
        }, false, typeScreen = typeScreen)
    } else {
        // OCR
        if (enableOCR.value && image.value != null) {
            // New ocr using ML Kit
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val inputImage = InputImage.fromBitmap(image.value!!, 0)

            showDialogLoader.value = true // Set the value to true before starting OCR processing

            recognizer.process(inputImage)
                .addOnSuccessListener { visionText ->
                    var r = parseText(visionText)
                    Log.d("OCR", r.toString())
                    card.value = if (typeScreen == TypeScreen.Wallet) user!!.createWallet() else user!!.createTicket()
                    card.value!!.image = image.value
                    card.value!!.title = r.first
                    card.value!!.price = r.second
                    card.value!!.date = r.third

                    showDialogLoader.value = false // Set the value back to false after OCR processing is completed
                    showEditCard.value = true
                }
        } else {
            PhotoGetter(onDismiss = {
                setShowDialog(false)
            }, onRetrieve = { img ->
                if (!enableOCR.value) {
                    card.value = if (typeScreen == TypeScreen.Wallet) user!!.createWallet() else user!!.createTicket()
                    card.value!!.image = img
                    showEditCard.value = true
                } else {
                    image.value = img
                }
            }, footer = {
                if (typeScreen == TypeScreen.Ticket) {
                    Spacer(modifier = Modifier.padding(12.dp))

                    LabelledCheckBox(checked = enableOCR.value, onCheckedChange = { checked ->
                        enableOCR.value = checked
                    }, label = "Activer l'OCR")
                }
            },
            typeScreen = typeScreen)
        }
    }
}