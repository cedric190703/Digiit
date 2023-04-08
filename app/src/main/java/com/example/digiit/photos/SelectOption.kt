package com.example.digiit.photos

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

enum class TypeScreen {
    Ticket, Wallet
}

@Composable
fun SelectOption(setShowDialog: (Boolean) -> Unit, user: User?, typeScreen: TypeScreen){
    val context = LocalContext.current

    val enableOCR = remember { mutableStateOf(false) }

    val showEditCard = remember { mutableStateOf(false) }

    val image = remember { mutableStateOf<Bitmap?>(null) }
    val card = remember { mutableStateOf<Card?>(null) }

    if (showEditCard.value && card.value != null) {
        EditCard(card.value!!, {
            setShowDialog(false)
        }, {
            setShowDialog(false)
        }, false)
    } else {
        // OCR
        if (enableOCR.value && image.value != null) {
            // New ocr using ML Kit
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val inputImage = InputImage.fromBitmap(image.value!!, 0)
            recognizer.process(inputImage)
                .addOnSuccessListener { visionText ->
                    var r = parseText(visionText)
                    Log.d("OCR", r.toString())
                    card.value = if (typeScreen == TypeScreen.Wallet) user!!.createWallet() else user!!.createTicket()
                    card.value!!.image = image.value
                    card.value!!.title = r.first
                    card.value!!.price = r.second
                    card.value!!.date = r.third


                    }
                    showEditCard.value = true

            /*

            // Convert bitmap to byte array
            val outputStream = ByteArrayOutputStream()
            image.value!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val byteArray = outputStream.toByteArray()



            // Create file from byte array
            val file = File(context.cacheDir, "image.jpg")
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(byteArray)
            fileOutputStream.close()


            // Close the stream
            outputStream.close()
            val apiUrl = "https://ocr-3szz.onrender.com/"

            // Store the API response in a State variable
            var apiResponse by remember { mutableStateOf<ApiResponse?>(null) }

            // Use LaunchedEffect to call getApiResponse asynchronously
            LaunchedEffect(apiUrl, file) {
                try {
                    apiResponse = withContext(Dispatchers.Default) {
                        withTimeoutOrNull(60000L) {
                            getApiResponse(imageFile = file, apiUrl = apiUrl)
                        } ?: ApiResponse(
                            status = "error",
                            message = "Timeout reached",
                            title = "",
                            time = "",
                            date = "",
                            total = ""
                        )
                    }
                } catch (e: Exception) {
                    Log.d("Error", "API error")
                } finally {
                    // Call EditCard when apiResponse is not null
                    // OCR => only for the ticket part
                    card.value = user!!.createTicket()
                    card.value!!.image = image.value
                    apiResponse?.let { apiResponse ->
                        if(apiResponse.status == "success") {
                            Log.d("results", "${apiResponse.title} | ${apiResponse.total}")
                            card.value!!.title = apiResponse.title.orEmpty()
                            card.value!!.price = apiResponse.total?.toFloat() ?: 0f
                        } else {
                            card.value!!.title = ""
                            card.value!!.price = 0f
                        }
                    }
                    showEditCard.value = true
                }
            }

            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(text = "Loading")
                },
                text = {
                    LottieLoadingAnimation()
                },
                buttons = {}
            )*/
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

                    /*ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        text = {  Text(text = "Détection du ticket",
                            fontSize = 17.sp) },
                        onClick = {
                            enableOCR.value = !enableOCR.value
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        icon = {
                            Icon(painter = painterResource(id = R.drawable.ocr_scanner),
                                "Logo select a file",
                                modifier = Modifier.padding(5.dp).size(35.dp),
                                tint = Color.White)
                        }
                    )*/
                }
            })
        }
    }
}