package com.example.digiit.navigationlogin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.digiit.R

@Composable
fun CreateAccount(
    onClick: (String, String, String) -> Unit,
    onLogin: () -> Unit
) {
    val emailVal = remember { mutableStateOf("") }
    val passwordVal = remember { mutableStateOf("") }
    val nomVal = remember { mutableStateOf("") }
    val prenomVal = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter)
    {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .width(2000.dp)
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Box() {
                Button(modifier = Modifier
                    .align(Alignment.TopStart),
                    shape = RoundedCornerShape(60),
                    onClick = { onLogin() }) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "array back logo logo",
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.create_account),
                    contentDescription = "logo cr??er un compte",
                    modifier = Modifier
                        .width(2000.dp)
                        .height(280.dp)
                        .padding(20.dp)
                        .align(Alignment.TopCenter)
                )
            }
            Text(
                text = "Cr??er un compte",
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                fontSize = MaterialTheme.typography.h4.fontSize
            )
            Spacer(modifier = Modifier.padding(1.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = nomVal.value,
                    onValueChange = { nomVal.value = it },
                    label = { Text(text = "Nom") },
                )
                OutlinedTextField(
                    value = prenomVal.value,
                    onValueChange = { prenomVal.value = it },
                    label = { Text(text = "Pr??nom") },
                )
                OutlinedTextField(
                    value = emailVal.value,
                    onValueChange = { emailVal.value = it },
                    label = { Text(text = "Addresse email") },
                )
                OutlinedTextField(
                    value = passwordVal.value,
                    onValueChange = { passwordVal.value = it },
                    label = { Text(text = "Mot de passe") },
                    placeholder = { Text(text = "mot de passe") }
                )
                Spacer(modifier = Modifier.padding(17.dp))
                Button(onClick = { onClick(emailVal.value, passwordVal.value, prenomVal.value) },
                    modifier = Modifier
                        .height(45.dp)) {
                    Text(text = "Cr??er un compte",
                        fontSize = MaterialTheme.typography.h6.fontSize)
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Se connecter",
                    textDecoration = TextDecoration.Underline,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.primary,
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}
