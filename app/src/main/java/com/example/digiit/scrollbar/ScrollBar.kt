package com.example.digiit.scrollbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.scrollbar(
    state: LazyListState,
    alignEnd: Boolean = true,
    thickness: Dp = 4.dp,
    fixedKnobRatio: Float? = null,
    knobCornerRadius: Dp = 4.dp,
    trackCornerRadius: Dp = 2.dp,
    knobColor: Color = Color(55, 55, 56, 235),
    trackColor: Color = Color.White,
    padding: Dp = 0.dp,
    visibleAlpha: Float = 1f,
    hiddenAlpha: Float = 0f,
    fadeInAnimationDurationMs: Int = 150,
    fadeOutAnimationDurationMs: Int = 500,
    fadeOutAnimationDelayMs: Int = 1000,
): Modifier {
    val targetAlpha =
        if (state.isScrollInProgress) {
            visibleAlpha
        } else {
            hiddenAlpha
        }
    val animationDurationMs =
        if (state.isScrollInProgress) {
            fadeInAnimationDurationMs
        } else {
            fadeOutAnimationDurationMs
        }
    val animationDelayMs =
        if (state.isScrollInProgress) {
            0
        } else {
            fadeOutAnimationDelayMs
        }

    val alpha by
    animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec =
        tween(delayMillis = animationDelayMs, durationMillis = animationDurationMs))

    return drawWithContent {
        drawContent()
        state.layoutInfo.visibleItemsInfo.firstOrNull()?.let { firstVisibleItem ->
            if (state.isScrollInProgress || alpha > 0f) {
                val viewportSize = size.height - padding.toPx() * 2

                val firstItemSize = firstVisibleItem.size
                val estimatedFullListSize = firstItemSize * state.layoutInfo.totalItemsCount

                val viewportOffsetInFullListSpace =
                    state.firstVisibleItemIndex * firstItemSize + state.firstVisibleItemScrollOffset

                val knobPosition =
                    (viewportSize / estimatedFullListSize) * viewportOffsetInFullListSpace + padding.toPx()
                val knobSize =
                    fixedKnobRatio?.let { it * viewportSize }
                        ?: (viewportSize * viewportSize) / estimatedFullListSize

                drawRoundRect(
                    color = trackColor,
                    topLeft =
                    when {
                        alignEnd -> Offset(size.width - thickness.toPx(), padding.toPx())
                        else -> Offset(0f, padding.toPx())
                    },
                    size = Size(thickness.toPx(), size.height - padding.toPx() * 2),
                    alpha = alpha,
                    cornerRadius = CornerRadius(x = trackCornerRadius.toPx(), y = trackCornerRadius.toPx()),
                )
                drawRoundRect(
                    color = knobColor,
                    topLeft =
                    when {
                        alignEnd -> Offset(size.width - thickness.toPx(), knobPosition)
                        else -> Offset(0f, knobPosition)
                    },
                    size = Size(thickness.toPx(), knobSize),
                    alpha = alpha,
                    cornerRadius = CornerRadius(x = knobCornerRadius.toPx(), y = knobCornerRadius.toPx()),
                )
            }
        }
    }
}