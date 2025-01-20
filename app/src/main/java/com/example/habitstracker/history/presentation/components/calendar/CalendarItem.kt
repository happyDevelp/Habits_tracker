package com.example.habitstracker.history.presentation.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.blueColor

@Composable
fun CalendarItem(
    modifier: Modifier = Modifier,
    day: String = "",
) {
    val color = blueColor

    var isSelected:Boolean by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .height(35.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Transparent)
            .clickable { isSelected = !isSelected },
        contentAlignment = Alignment.Center
    ) {
        val isInt = day.toIntOrNull() != null
        MyText(
            modifier = modifier.drawBehind {
                if (isSelected)
                    drawCircle(
                        radius = this.size.maxDimension / 1.4f,
                        color = color
                    )
                else {
                    drawCircle(
                        radius = this.size.maxDimension / 0,
                        color = color
                    )
                }
            },
            text = day,
            textSize = 15.sp,
            color = if (!isInt || isSelected) Color.White else Color.White.copy(0.75f)
        )
    }
}

@Composable
fun SpacerItem() {
    CalendarItem()
}