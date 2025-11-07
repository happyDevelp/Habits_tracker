package com.example.habitstracker.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

object BirdAnimations {
    @Composable
    fun HappyBird(modifier: Modifier = Modifier) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.Asset("happy_bird.json")
        )
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true
        )

        LottieAnimation(
            modifier = modifier,
            composition = composition,
            progress = { progress },
            contentScale = ContentScale.Crop
        )
    }
}
