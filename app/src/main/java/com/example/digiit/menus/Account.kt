package com.example.digiit.menus

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.digiit.data.UserProvider
import com.example.digiit.photos.PhotoGetter
import com.example.digiit.photos.TypeScreen
import es.dmoral.toasty.Toasty

@Composable
fun EditAccount(
    onDismiss: (Boolean) -> Unit,
    auth: UserProvider
) {
    val ctx = LocalContext.current

    val photo = remember { mutableStateOf(auth.user!!.getImageBitmapOrDefault(ctx)) }
    val name = remember { mutableStateOf(auth.user!!.name) }
    val lastname = remember { mutableStateOf(auth.user!!.lastname) }
    val email = remember { mutableStateOf(auth.user!!.email) }

    val showImageGetter = remember { mutableStateOf(false) }

    // Change this value to have the real maxValueSlider
    // TODO
    val maxValueSlider = remember { mutableStateOf("1000") }

    Dialog(
        onDismissRequest = { onDismiss(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
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
                        bitmap = photo.value,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )
                    IconButton(
                        onClick = {
                            showImageGetter.value = true
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
                    label = { Text(text = "Prénom") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onBackground)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = lastname.value,
                    onValueChange = { lastname.value = it },
                    label = { Text(text = "Nom") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onBackground))
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text(text = "Mail") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onBackground))
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = maxValueSlider.value,
                    onValueChange = { maxValueSlider.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Montant maximum pour ce mois") },
                    placeholder = { Text(text = "Montant maximum pour ce mois") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onBackground)
                )
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .height(85.dp)
                        .padding(vertical = 18.dp, horizontal = 4.dp),
                    text = {  Text(text = "Modifier", fontSize = 18.sp) },
                    onClick = {
                        auth.user!!.email = email.value
                        auth.user!!.name = name.value
                        auth.user!!.lastname = lastname.value
                        auth.user!!.profilePicture = photo.value.asAndroidBitmap()
                        auth.user!!.save { err1 ->
                            if (err1 == null) {
                                auth.user!!.saveProfilePicture { err2 ->
                                    if (err2 == null) {
                                        Toasty.success(ctx, "Les paramètres de l'utilisateur ont bien été modifiées", Toast.LENGTH_SHORT, true).show()
                                    } else {
                                        Toasty.error(ctx, "Impossible de modifiées la photo de profile de l'utilisateur", Toast.LENGTH_SHORT, true).show()
                                    }
                                }
                            } else {
                                Toasty.error(ctx, "Impossible de modifiées les paramètres de l'utilisateur", Toast.LENGTH_SHORT, true).show()
                            }
                        }
                        onDismiss(false)
                    },
                    backgroundColor = MaterialTheme.colors.primary
                )
            }
        }
    }

    if (showImageGetter.value) {
        PhotoGetter(onDismiss = {
            showImageGetter.value = false
        }, onRetrieve = { img ->
            showImageGetter.value = false
            photo.value = img.asImageBitmap()
        }, typeScreen = TypeScreen.Ticket)
    }
}
