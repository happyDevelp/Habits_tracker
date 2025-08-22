package com.example.habitstracker.history.presentation.components.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.habit.domain.DateHabitEntity
import java.time.LocalDate

@Composable
fun HistoryCalendarDay(
    date: LocalDate?,
    habits: List<DateHabitEntity>,
    onClick: (LocalDate) -> Unit
) {
    val progress = remember(habits) { dayProgress(habits) }
    val isPerfect = progress == 1f
    val isToday = date == LocalDate.now()
    val progressColor = MaterialTheme.colorScheme.primaryContainer

    val clickable = date != null

    Box(
        modifier = Modifier
            .size(40.dp) // The size of the circle
            .clip(CircleShape)
            .let { m -> if (clickable)m .clickable { onClick(date!!) } else m },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // 1) gray background rings if there is something done
            if (progress > 0f) {
                drawArc(
                    color = Color(0xC9494949),
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            // 2) A half transparent background for today
            if (isToday) {
                drawCircle(
                    radius = size.maxDimension / 2.2f,
                    color = Color(0xC1565656)
                )
            }
            // 3) full circle for the perfect day
            if (isPerfect) {
                drawCircle(
                    radius = size.maxDimension / 2.2f,
                    color = progressColor
                )
            }
            // 4) The arc of progress
            if (progress > 0f) {
                drawArc(
                    color = progressColor,
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    style = Stroke(width = 11.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        Text(
            text = date?.dayOfMonth?.toString() ?: "",
            color = Color.White,
            fontFamily = PoppinsFontFamily,
            fontSize = 16.sp
        )
    }
}

private fun dayProgress(list: List<DateHabitEntity>?): Float {
    val l = list ?: return 0f
    if (l.isEmpty()) return 0f
    val done = l.count { it.isCompleted }
    return done.toFloat() / l.size.toFloat()          // 0f..1f
}