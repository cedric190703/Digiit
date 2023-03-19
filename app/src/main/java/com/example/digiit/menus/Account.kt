package com.example.digiit.menus

import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.google.firebase.auth.FirebaseAuth
import es.dmoral.toasty.Toasty

@Composable
fun EditAccount(onDismiss: (Boolean) -> Unit) {
    // Get real data from database
    // get current user

    val  user = FirebaseAuth.getInstance().currentUser
    val account = remember { Account(user?.displayName.toString(), "Prenom", user?.email.toString(), "46.69$") }
    // TODO : create a db to store other account data



    var name = remember { mutableStateOf(account.name) }
    var prenom = remember { mutableStateOf(account.prenom) }
    var email = remember { mutableStateOf(account.email) }
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
                        onClick = { /* TODO */ },
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
                    label = { Text(text = "${account.name}") })
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = prenom.value,
                    onValueChange = { prenom.value = it },
                    label = { Text(text = "${account.prenom}") })
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text(text = "${account.email}") })
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Argent dépensé ce mois-ci: ",
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "${account.payments}",
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
                        account.email = email.value
                        account.name = name.value
                        account.prenom = prenom.value
                        onDismiss(false)
                        Toasty.success(ctx, "Les paramètres de l'utilisateur ont bien été modifiées", Toast.LENGTH_SHORT, true).show() },
                    backgroundColor = MaterialTheme.colors.primary
                )
            }
        }
    }
}

data class Account(var name: String, var prenom: String, var email: String, val payments: String)