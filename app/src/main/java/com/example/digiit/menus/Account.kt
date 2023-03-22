package com.example.digiit.menus

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.digiit.R
import com.example.digiit.data.UserProvider
import com.example.digiit.photos.createBitmapFromUri
import com.example.digiit.photos.rotateBitmap
import es.dmoral.toasty.Toasty


@Composable
fun EditAccount(onDismiss: (Boolean) -> Unit, auth: UserProvider) {
    // photoUri for the file
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        photoUri = uri
    }

    val name = remember { mutableStateOf(auth.user!!.name) }
    val lastname = remember { mutableStateOf(auth.user!!.lastname) }
    val email = remember { mutableStateOf(auth.user!!.email) }
    val ctx = LocalContext.current
    Dialog(
        onDismissRequest = { onDismiss(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { onDismiss(false) }) {
                        Icon(imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(38.dp))
                    }
                }
                Box(
                    modifier = Modifier
                        .width(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )
                    IconButton(
                        onClick = {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Informations du compte",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Divider(Modifier.padding(bottom = 8.dp))
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text(text = auth.user!!.name) })
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = lastname.value,
                    onValueChange = { lastname.value = it },
                    label = { Text(text = auth.user!!.lastname) })
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text(text = auth.user!!.email) })
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Argent dépensé ce mois-ci: ",
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "--.--",
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .height(85.dp)
                        .padding(vertical = 18.dp, horizontal = 4.dp),
                    text = {  Text(text = "Modifier élement", fontSize = 18.sp) },
                    onClick = {
                        auth.user!!.email = email.value
                        auth.user!!.name = name.value
                        auth.user!!.lastname = lastname.value
                        auth.user!!.save { err ->
                            if (err != null) {
                                Toasty.error(ctx, "Impossible de modifiées les paramètres de l'utilisateur", Toast.LENGTH_SHORT, true).show()
                            } else {
                                Toasty.success(ctx, "Les paramètres de l'utilisateur ont bien été modifiées", Toast.LENGTH_SHORT, true).show()
                            }
                        }
                        onDismiss(false)
                    },
                    backgroundColor = MaterialTheme.colors.primary
                )
            }
        }
        if (photoUri != null) {
            val bitmapTmp: Bitmap =
                createBitmapFromUri(context = LocalContext.current, uri = photoUri)
            val bitmap = rotateBitmap(bitmapTmp)
            // use the bitmap for the User here
            // TODO
        }
    }
}
