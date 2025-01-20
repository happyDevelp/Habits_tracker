package com.example.habitstracker.habit.presentation.history.tab_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.screensBackgroundDark

@Preview(showSystemUi = true)
@Composable
fun AllHabitsScreenPreview(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize().background(screensBackgroundDark))
    { AllHabitScreen() }
}

@Composable
fun AllHabitScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Screen All habits",
        color = Color.White,
        fontSize = 30.sp,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        textAlign = TextAlign.Center
    )
}