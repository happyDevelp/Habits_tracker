package com.example.habitstracker.habit.presentation.today_main.components.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.MyFontFamily
import com.example.habitstracker.habit.domain.ShownHabit
import java.time.LocalDate

@Composable
fun CalendarItem(
    todayHabits: List<ShownHabit>,
    date: LocalDate? = LocalDate.now(),
    isSelected: Boolean = true,
    onItemClicked: (() -> Unit)? = null,
) {
    val completedCount = todayHabits.count { it.isSelected }
    val totalCount = todayHabits.count()

    val progress = if (totalCount == 0) 0f else (completedCount.toFloat() / totalCount.toFloat())
    val progressColor = MaterialTheme.colorScheme.primaryContainer

    Box(modifier = Modifier.size(width = 50.dp, height = 70.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .clickable { onItemClicked?.invoke() }
                ) {
                    if (completedCount > 0) {
                        drawArc(
                            color = Color(0xDF333333),
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                    if (date == LocalDate.now()) {
                        drawCircle(
                            radius = this.size.maxDimension / 1.8f,
                            color = Color(0xC1565656),
                        )
                    }
                    if (progress == 1f) {
                        drawCircle(
                            radius = this.size.maxDimension / 1.8f,
                            color = progressColor,
                        )
                    }
                    drawArc(
                        color = progressColor,
                        startAngle = -90f,
                        sweepAngle = 360f * progress,
                        useCenter = false,
                        style = Stroke(width = 9.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                Text(
                    color = Color.White,
                    text = date?.dayOfMonth.toString(),
                    fontSize = 20.sp,
                    fontFamily = MyFontFamily,
                    fontWeight = FontWeight.Bold,
                )
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 6.dp)
                        .height(6.dp)
                        .width(30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(progressColor)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF242C33)
@Composable
private fun Preview() {
    AppTheme(darkTheme = true) {
        CalendarItem(todayHabits = listOf(ShownHabit(0, 0,"preview", "aaa")))
    }
}