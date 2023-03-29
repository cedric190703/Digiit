package com.example.digiit.utils

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp

@Composable
fun CustomProgressBar(
    progress: Float,
    maxValue: Float,
    color: Color,
    cornerRadius: Dp = 9.dp,
    labelText: String? = null,
    labelStyle: TextStyle = TextStyle.Default,
    animationDurationMillis: Int = 1000,
    animationDelayMillis: Int = 200,
    animationEasing: Easing = LinearOutSlowInEasing
) {
    val newProgress = if (progress > maxValue) maxValue else progress
    val size by animateFloatAsState(
        targetValue = newProgress / maxValue,
        animationSpec = tween(
            durationMillis = animationDurationMillis,
            delayMillis = animationDelayMillis,
            easing = animationEasing
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (labelText != null) {
            Text(
                text = labelText,
                style = labelStyle,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        // Progress Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(18.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            // Background of the progress bar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(Color(0xFFD9DADA))
            )
            // Progress of the progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth(size)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color)
                    .animateContentSize()
            )
            if (labelText == null) {
                Text(
                    text = "${(progress / maxValue * 100).toInt()}%",
                    style = TextStyle(fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if(progress / maxValue  < 0.6f) MaterialTheme.colors.primary else Color.White),
                    modifier = Modifier.padding(horizontal = 8.dp).wrapContentSize(Alignment.Center).fillMaxSize().wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}
