package com.example.digiit.navigation

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
import androidx.navigation.NavHostController
import com.example.digiit.R
import com.example.digiit.Screen

@Composable
fun createAccount(navController : NavHostController) {
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
            Image(
                painter = painterResource(id = R.drawable.lost_password),
                contentDescription = "logo créer un compte",
                modifier = Modifier
                    .width(2000.dp)
                    .height(280.dp)
                    .padding(20.dp)
            )
            Text(
                text = "Créer un compte",
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                fontSize = MaterialTheme.typography.h4.fontSize
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = emailVal.value,
                    onValueChange = { emailVal.value = it },
                    label = { Text(text = "Nom") },
                )
                OutlinedTextField(
                    value = emailVal.value,
                    onValueChange = { emailVal.value = it },
                    label = { Text(text = "Prénom") },
                )
                OutlinedTextField(
                    value = emailVal.value,
                    onValueChange = { emailVal.value = it },
                    label = { Text(text = "Addresse email") },
                )
                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedTextField(
                    value = passwordVal.value,
                    onValueChange = { passwordVal.value = it },
                    label = { Text(text = "Mot de passe") },
                    placeholder = { Text(text = "mot de passe") }
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Button(onClick = { },
                    modifier = Modifier
                        .height(45.dp)) {
                    Text(text = "Créer un compte",
                        fontSize = MaterialTheme.typography.h6.fontSize)
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Se connecter",
                    textDecoration = TextDecoration.Underline,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.primary,
                    fontSize = MaterialTheme.typography.h4.fontSize,
                    modifier = Modifier
                        .clickable { navController.navigate(Screen.HomeLogin.route) }
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}