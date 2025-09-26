package com.example.habitstracker.habit.presentation.today_main.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Composable
fun ConfettiEffect() {
    val spread = 180
    val startSpeed = 0f
    val maxSpeed = 35f
    val damping = 0.89f
    val duration: Long = 2000 // milliseconds

    KonfettiView(
        modifier = Modifier.fillMaxSize(),
        parties = listOf(
            // left
            Party(
                angle = 200,
                spread = spread,
                speed = startSpeed, // start speed
                maxSpeed = maxSpeed,
                damping = damping,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = duration, TimeUnit.MILLISECONDS).perSecond(150),
                position = Position.Relative(1.0, 0.1) // position: left
            ),
            // right
            Party(
                angle = 340,
                spread = spread,
                speed = startSpeed, // start speed
                maxSpeed = maxSpeed,
                damping = damping,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = duration, TimeUnit.MILLISECONDS).perSecond(150),
                position = Position.Relative(0.0, 0.1) // position: right
            )
        )
    )
}