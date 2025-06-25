package com.example.habitstracker.history.presentation.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.MyText
import java.time.LocalDate

@Composable
fun CalendarItem(
    modifier: Modifier = Modifier,
    date: LocalDate? = null,
) {
    val navController = LocalNavController.current
    Box(
        modifier = modifier
            .height(35.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Transparent)
            .clickable {
                navController.navigate(
                    if (date != null) {
                        Route.Today(
                            historyDate = date.toString()
                        )
                    } else Route.Today()
                )
            },
        contentAlignment = Alignment.Center
    ) {
        MyText(
            text = date?.dayOfMonth?.toString() ?: "",
            textSize = 15.sp,
        )
    }
}

@Composable
fun SpacerItem() {
    CalendarItem()
}