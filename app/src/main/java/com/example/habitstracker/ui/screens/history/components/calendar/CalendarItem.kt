package com.example.habitstracker.ui.screens.history.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.ui.custom.MyText
import com.example.habitstracker.ui.theme.PoppinsFontFamily

@Preview
@Composable
fun CalendarItem(modifier: Modifier = Modifier, day: String = "") {
    Box(
        modifier = modifier
            .height(30.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        val isInt = day.toIntOrNull() != null
        MyText(text = day, textSize = 15.sp, color = if (isInt) Color.White.copy(0.7f) else Color.White)
    }
}

@Composable
fun SpacerItem(modifier: Modifier = Modifier) {
    CalendarItem()
}