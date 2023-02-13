package com.example.digiit.photos

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.digiit.R
import com.example.digiit.TicketInfo.dialogTicketInfo
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.util.ArrayList

@Composable
fun SelectOption(setShowDialog: (Boolean) -> Unit){
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        photoUri = uri
    }
    val showDialogPhoto = remember { mutableStateOf(false) }
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sélectionner une action",
                            fontSize = 23.sp
                        )
                        OutlinedButton(onClick = { setShowDialog(false) },
                            modifier= Modifier.size(33.dp),
                            shape = CircleShape,
                            border= BorderStroke(1.dp, Color.Red),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Red)
                        ) {
                            Icon(modifier = Modifier.size(28.dp),
                                imageVector = Icons.Default.Close,
                                contentDescription = "icon description")
                        }
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.height(65.dp),
                        text = {  Text(text = "Sélectionner une photo",
                            fontSize = 17.sp) },
                        onClick = { launcher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                        backgroundColor = MaterialTheme.colors.primary,
                        icon = {
                            Icon(
                                painterResource(id = R.drawable.select_photo),
                                "Logo select photo",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(20.dp),
                                tint = Color.White)
                        }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.height(65.dp),
                        text = {  Text(text = "Prendre une photo",
                            fontSize = 17.sp) },
                        onClick = { showDialogPhoto.value = true },
                        backgroundColor = MaterialTheme.colors.primary,
                        icon ={
                            Icon(painter = painterResource(id = R.drawable.take_photo),
                                "Logo take photo",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(20.dp),
                                tint = Color.White)
                        }
                    )
                    if (photoUri != null) {
                        val painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = photoUri)
                                .build()
                        )
                        showDialogPhoto.value = true
                        if(showDialogPhoto.value)
                        {
                            dialogTicketInfo(setShowDialogPhoto = {
                                showDialogPhoto.value = it
                                println(showDialogPhoto.value)
                            }, painter)
                        }
                    }
                }
            }
        }
    }
}