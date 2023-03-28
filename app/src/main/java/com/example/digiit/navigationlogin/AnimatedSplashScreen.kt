package com.example.digiit.navigationlogin

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.digiit.navgraphs.AuthScreen
import com.example.digiit.R

fun skipSplashScreen(navController : NavHostController) {
    navController.navigate(AuthScreen.Login.route)
}

@Composable
fun AnimatedSplashScreen(navController : NavHostController) {
    val startAnimation = remember { mutableStateOf(false) }
    val anim = animateFloatAsState(
        targetValue = if(startAnimation.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1500
        ),
        finishedListener = { skipSplashScreen(navController) }
    )
    LaunchedEffect(0) {
        startAnimation.value = true
    }
    Splash(anim = anim) { skipSplashScreen(navController) }
}

@Composable
fun Splash(anim: State<Float>, skip: () -> Unit) {
    Box(modifier = Modifier
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
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Right,
                fontSize = MaterialTheme.typography.h1.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(alpha = anim.value)
            )
        }
    }
}