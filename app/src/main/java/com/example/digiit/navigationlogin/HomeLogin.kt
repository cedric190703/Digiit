package com.example.digiit.navigationlogin

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.digiit.R
import com.example.digiit.data.UserProvider
import com.example.digiit.data.user.RemoteUser
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import es.dmoral.toasty.Toasty

@Composable
fun HomeLogin(
    auth: UserProvider,
    onClickGoHome: () -> Unit,
    onClickSignUp: () -> Unit,
    onClickForget: () -> Unit
) {
    val emailVal = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = androidx.compose.ui.platform.LocalContext.current
    val passwordVisibility = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "logo créer un compte",
            modifier = Modifier
                .width(2000.dp)
                .height(290.dp)
                .padding(20.dp)
        )
        Text(
            text = "Se connecter",
            style = TextStyle(
                fontWeight = FontWeight.Bold
            ),
            fontSize = MaterialTheme.typography.h4.fontSize
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = emailVal.value,
                onValueChange = { emailVal.value = it },
                label = { Text(text = "Addresse email") },
            )

            Spacer(modifier = Modifier.padding(5.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text(text = "Mot de passe") },
                placeholder = { Text(text = "mot de passe") },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                    }
                },
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                else PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
                onClick = {
                    //check if email address is correctly formatted
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailVal.value).matches()) {
                        Toasty.error(context, "Adresse email invalide", Toast.LENGTH_SHORT, true).show()
                        return@Button
                    }
                    isLoading.value = true
                    auth.loginRemoteUser(emailVal.value, password.value) { error, _ ->
                        if (error == null) {
                            Toasty.success(context, "Connexion réussie", Toast.LENGTH_SHORT, true).show()
                            onClickGoHome()
                        } else {
                            isLoading.value = false
                            Toasty.error(context, "Connexion échouée", Toast.LENGTH_SHORT, true).show()
                        }
                    }
                },
                modifier = Modifier,
                enabled = !isLoading.value
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                } else {
                    Text(
                        text = "Se connecter",
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Mot de passe oublié",
                textDecoration = TextDecoration.Underline,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colors.primary,
                fontSize = MaterialTheme.typography.h6.fontSize,
                modifier = Modifier
                    .clickable { onClickForget() }
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = "Créer un compte",
                textDecoration = TextDecoration.Underline,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colors.primary,
                fontSize = MaterialTheme.typography.h6.fontSize,
                modifier = Modifier
                    .clickable { onClickSignUp() }
            )
        }
    }
}