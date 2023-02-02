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
fun lostPassword(
    onClick: () -> Unit
) {
    val emailVal = remember { mutableStateOf("") }
    val password1 = remember { mutableStateOf("") }
    val password2 = remember { mutableStateOf("") }

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
                contentDescription = "logo password lost",
                modifier = Modifier
                    .width(2000.dp)
                    .height(280.dp)
                    .padding(20.dp)
            )
            Text(
                text = "Mot de passe oublié",
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
                    label = { Text(text = "Addresse email") },
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Entrer le nouveau mot de passe",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
                Spacer(modifier = Modifier.padding(3.dp))
                OutlinedTextField(
                    value = password1.value,
                    onValueChange = { password1.value = it },
                    label = { Text(text = "Mot de passe 1") },
                    placeholder = { Text(text = "mot de passe 1") }
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Retaper le mot de passe",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
                Spacer(modifier = Modifier.padding(3.dp))
                OutlinedTextField(
                    value = password2.value,
                    onValueChange = { password2.value = it },
                    label = { Text(text = "Mot de passe 2") },
                    placeholder = { Text(text = "mot de passe 2") }
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Button(onClick = { },
                    modifier = Modifier
                        .height(45.dp)) {
                    Text(text = "Changer mot de passe",
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
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    modifier = Modifier
                        .clickable { onClick() }
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}