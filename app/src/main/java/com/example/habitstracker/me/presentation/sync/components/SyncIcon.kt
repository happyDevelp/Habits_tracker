package com.example.habitstracker.me.presentation.sync.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun SyncIcon(syncInProcess: Boolean) {

    val infiniteTransition = rememberInfiniteTransition(label = "sync rotation")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val currentRotation = if (syncInProcess) rotation else 0f

    Icon(
        imageVector = Icons.Default.Sync,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier
            .size(20.dp)
            .graphicsLayer { rotationZ = currentRotation }
    )
}

