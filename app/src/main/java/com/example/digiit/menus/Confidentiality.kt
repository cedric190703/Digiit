package com.example.digiit.menus

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@ExperimentalMaterialApi
@Composable
fun Confidentiality(onDismiss: (Boolean) -> Unit) {
    val sections = listOf(
        "Collecte d'informations" to "Nous collectons des informations lorsque vous utilisez notre application, y compris des informations sur votre appareil et sur la façon dont vous interagissez avec l'application.",
        "Utilisation des informations" to "Nous utilisons les informations que nous collectons pour améliorer notre application et personnaliser votre expérience utilisateur. Nous ne vendons pas ces informations à des tiers.",
        "Protection des informations" to "Nous prenons des mesures de sécurité pour protéger les informations que nous collectons contre l'accès non autorisé, la modification, la divulgation ou la destruction.",
        "Cookies" to "Nous utilisons l'outil de gestion de données : Firebase qui utilise des cookies.",
        "Modifications de la politique" to "Nous pouvons mettre à jour cette politique de confidentialité de temps à autre en publiant une nouvelle version sur notre site web. Nous vous informerons de tout changement important apporté à cette politique de confidentialité.",
        "Contact" to "Si vous avez des questions ou des préoccupations concernant notre politique de confidentialité, veuillez nous contacter à l'adresse suivante : [adresse e-mail]."
    )
    Dialog(onDismissRequest = { onDismiss(false) }) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (section in sections) {
                    ExpandableCard(
                        title = section.first,
                        description = section.second,
                        padding = 8.dp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Button(
                    onClick = { onDismiss(false) },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxSize()
                ) {
                    Text("OK", fontSize = 20.sp)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = 17.sp,
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 7,
    padding: Dp = 12.dp
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    Card(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Text(
                    text = description,
                    fontSize = descriptionFontSize,
                    fontWeight = descriptionFontWeight,
                    maxLines = descriptionMaxLines
                )
            }
        }
    }
}