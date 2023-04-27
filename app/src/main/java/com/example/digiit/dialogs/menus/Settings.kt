package com.example.digiit.dialogs.menus

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@ExperimentalMaterialApi
@Composable
fun LightModeSwitch(
    isLightMode: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Mode d'éclairage",
            style = MaterialTheme.typography.h6
        )
        Switch(
            checked = isLightMode,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color.Yellow,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsElement(onDismiss: (Boolean) -> Unit) {
    val sections = listOf(
        "Cookies" to "Nous utilisons l'outil de gestion de données : Firebase qui utilise des cookies.",
        "Préférence marketing" to "Cette application est un projet d'étude réalisé par des étudiants.",
        "Notifications" to "Nus n'avons pas mis en place de système de notification pour le moment."
    )
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
                LightModeSwitch(isLightMode = isSystemInDarkTheme(), onToggle = {
                    // TODO
                })
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Pour en savoir plus sur la confidentialité, veuillez consulter la section Confidentialité de l'application.",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                for (section in sections) {
                    ExpandableCard(
                        title = section.first,
                        description = section.second,
                        padding = 8.dp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp,
                    backgroundColor = Color(0xFF3D3F3F)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Pour nous signaler une erreur :",
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            text = "Example@gmail.com",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Button(
                    onClick = { onDismiss(false) },
                    modifier = Modifier
                        .padding(top = 19.dp)
                        .fillMaxHeight()
                        .width(120.dp)
                ) {
                    Text("OK", fontSize = 20.sp)
                }
            }
        }
    }
}