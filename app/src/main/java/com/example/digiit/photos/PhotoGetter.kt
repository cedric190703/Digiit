package com.example.digiit.photos

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.digiit.R


fun loadBitmapFromUri(context: Context, uri: Uri?): Bitmap {
    val inputStream = uri?.let { context.contentResolver.openInputStream(it) }
    return BitmapFactory.decodeStream(inputStream)
}


@Composable
fun PhotoGetter(modifier: Modifier = Modifier, footer: @Composable () -> Unit = {}, onDismiss: () -> Unit, onRetrieve: (img: Bitmap) -> Unit) {
    val context = LocalContext.current

    val takePhotoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            @Suppress("DEPRECATION")
            val uri = result.data?.getParcelableExtra<Uri>("PHOTO_URI")
            if (uri != null) {
                onRetrieve(loadBitmapFromUri(context, uri))
            }
        }
    }

    val filePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        onRetrieve(loadBitmapFromUri(context, uri))
    }

    val mediaPickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        onRetrieve(loadBitmapFromUri(context, uri))
    }

    Dialog(onDismissRequest = onDismiss)  {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colors.background,
            modifier = modifier
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { onDismiss() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sélectionner une action",
                        fontSize = 27.sp
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        text = {
                            Text(
                                text = "Sélectionner une photo",
                                fontSize = 17.sp, fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = {
                            mediaPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        icon = {
                            Icon(
                                painterResource(id = R.drawable.select_photo),
                                "Logo select photo",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(35.dp),
                                tint = Color.White
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        text = {
                            Text(
                                text = "Prendre une photo",
                                fontSize = 17.sp
                            )
                        },
                        onClick = {
                            takePhotoLauncher.launch(Intent(context, TakePhoto::class.java))
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.take_photo),
                                "Logo take photo",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(35.dp),
                                tint = Color.White
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .height(85.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        text = {
                            Text(
                                text = "Sélectionner un fichier",
                                fontSize = 17.sp
                            )
                        },
                        onClick = {
                            filePickerLauncher.launch("application/pdf,image/jpeg,image/png,image/gif,image/bmp")
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.file),
                                "Logo select a file",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(35.dp),
                                tint = Color.White
                            )
                        }
                    )

                    footer()
                }
            }
        }
    }
}
