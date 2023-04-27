package com.example.digiit.screens.login

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.digiit.ApplicationData
import com.example.digiit.R
import com.example.digiit.navigation.Routes


fun skipSplashScreen(auth: ApplicationData) {
    auth.navigation.navigate(Routes.LOGIN.path)
}


@Composable
fun AnimatedSplashScreen(auth: ApplicationData) {
    val startAnimation = remember { mutableStateOf(false) }
    val anim = animateFloatAsState(
        targetValue = if(startAnimation.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1500
        ),
        finishedListener = { skipSplashScreen(auth) }
    )
    LaunchedEffect(0) {
        startAnimation.value = true
    }
    Splash(anim = anim) { skipSplashScreen(auth) }
}

@Composable
fun Splash(anim: State<Float>, skip: () -> Unit) {
    Box(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize().clickable {
            skip()
        },
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo application",
                modifier = Modifier
                    .width(240.dp)
                    .height(250.dp)
                    .alpha(alpha = anim.value)
            )
            Text(
                text = "Digiit",
                color = Color(0xFF0139CE),
                textAlign = TextAlign.Right,
                fontSize = MaterialTheme.typography.h1.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(alpha = anim.value)
            )
        }
    }
}