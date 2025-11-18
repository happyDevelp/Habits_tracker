package com.example.habitstracker.me.presentation.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.habitstracker.me.presentation.sync.SyncButtonState

@Composable
fun SyncIcon(state: SyncButtonState) {
    val rotation = remember { Animatable(0f) }

    // If loading then run an infinite animation
    LaunchedEffect(state) {
        if (state == SyncButtonState.LOADING) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(700, easing = LinearEasing)
                )
            )
        } else {
            rotation.snapTo(0f)
        }
    }

    val color = when (state) {
        SyncButtonState.IDLE -> Color(0xFF407BFF)
        SyncButtonState.LOADING -> Color(0xFF407BFF)
        SyncButtonState.SUCCESS -> Color(0xFF00C853)
        SyncButtonState.ERROR -> Color(0xFFFF1744)
    }

    Icon(
        modifier = Modifier
            .size(26.dp)
            .graphicsLayer {
                rotationZ = rotation.value
            },
        imageVector = Icons.Default.Sync,
        contentDescription = "Sync",
        tint = color
    )
}