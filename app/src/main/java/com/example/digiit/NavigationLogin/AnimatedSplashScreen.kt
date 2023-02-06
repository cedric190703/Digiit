package com.example.digiit

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.navigation.NavHostController
import com.example.digiit.NavGraphs.AuthScreen
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController : NavHostController) {
    var startAnimation by remember { mutableStateOf(false)}
    val anim = animateFloatAsState(
        targetValue = if(startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 4000
        )
    )
    LaunchedEffect(key1 = true)
    {
        startAnimation = true
        delay(3800)
        navController.navigate(AuthScreen.Login.route)
    }
    Splash(anim = anim.value)
}

@Composable
fun Splash(anim: Float) {
    Box(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo application",
                modifier = Modifier
                    .width(240.dp)
                    .height(250.dp)
                    .alpha(alpha = anim)
            )
            Text(
                text = "Digiit",
                color = Color.White,
                textAlign = TextAlign.Right,
                fontSize = MaterialTheme.typography.h1.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(alpha = anim)
            )
        }
    }
}