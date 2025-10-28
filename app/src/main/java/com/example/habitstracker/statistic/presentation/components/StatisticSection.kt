package com.example.habitstracker.statistic.presentation.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.MyPalette
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import java.time.LocalDate

@Composable
fun WeekAvgCompletion(
    modifier: Modifier = Modifier,
    height: Dp,
    percentageList: List<Float>,
    monday: LocalDate,
    sunday: LocalDate
) {
    val horizontalPadding = 12.dp

    Card(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerBackgroundDark),
    ) {
        Row(
            modifier = modifier
                .padding(top = 16.dp)
                .padding(horizontal = horizontalPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                MyText(
                    text = "${monday.dayOfMonth} ${
                        monday.month.toString()
                            .take(3).lowercase().replaceFirstChar { it.uppercase() }
                    } - ${sunday.dayOfMonth} ${
                        monday.month.toString()
                            .take(3).lowercase().replaceFirstChar { it.uppercase() }
                    }",
                    textSize = 17.sp
                )
                Text(
                    text = monday.year.toString(),
                    fontSize = 12.5.sp,
                    color = Color.White.copy(0.6f),
                    fontFamily = PoppinsFontFamily,
                )
            }

            Column(horizontalAlignment = Alignment.End) {

                val avg = (percentageList.average() * 100).toInt().toString() + "%"
                MyText(text = avg, textSize = 17.sp)
                Text(
                    text = stringResource(R.string.statistic_avg_completion_rate),
                    fontSize = 12.5.sp,
                    color = Color.White.copy(0.6f),
                    fontFamily = PoppinsFontFamily,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = horizontalPadding),
            contentAlignment = Alignment.BottomStart
        ) {
            WeeklyBars(percentageList = percentageList)
        }
    }
}


@Composable
private fun WeeklyBars(
    percentageList: List<Float>,
    barWidth: Dp = 24.dp,
    barMaxHeight: Dp = 150.dp,
    topPadding: Dp = 4.dp,
    labelGap: Dp = 8.dp,
    labelSlot: Dp = 20.dp
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(topPadding + barMaxHeight + labelSlot)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val stroke = 1.dp.toPx()
            val chartHeight = barMaxHeight.toPx()
            val textPaint = Paint().apply {
                color = android.graphics.Color.WHITE
                textSize = 28f
                alpha = 180
            }

            for (percent in 20..100 step 20) {
                val y = topPadding.toPx() + (1f - percent / 100f) * chartHeight

                drawLine(
                    color = Color.Gray.copy(alpha = if (percent == 100) 0.7f else 0.4f),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                    strokeWidth = stroke
                )

                drawContext.canvas.nativeCanvas.drawText(
                    "$percent%",
                    0f,
                    y - 4f,
                    textPaint
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            val weekDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

            percentageList.forEachIndexed { index, percentage ->
                BarWithTooltip(
                    index = index,
                    selectedIndex = selectedIndex,
                    onSelect = { i ->
                        selectedIndex = if (selectedIndex == i) null else i
                    },
                    percentage = percentage.coerceIn(0f, 1f),
                    label = weekDays[index],
                    barWidth = barWidth,
                    barMaxHeight = barMaxHeight,
                    labelGap = labelGap
                )
            }
        }
    }
}

@Composable
private fun BarWithTooltip(
    index: Int,
    selectedIndex: Int?,
    onSelect: (Int) -> Unit,
    percentage: Float,
    label: String,
    barWidth: Dp,
    barMaxHeight: Dp,
    labelGap: Dp
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(barWidth)
                .height(barMaxHeight),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(if (percentage == 0f) 0.03f else percentage)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(if (percentage == 0f) Color.Gray.copy(0.8f) else MyPalette.blueColor)
                    .clickable { onSelect(index) }
            )

            if (selectedIndex == index) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-24).dp)
                ) {
                    Text(
                        text = "${(percentage * 100).toInt()}%",
                        color = Color.White,
                        fontSize = 8.7.sp,
                        fontFamily = PoppinsFontFamily,
                    )
                }
            }
        }

        Spacer(Modifier.height(labelGap))

        Text(
            text = label,
            color = Color.White.copy(0.75f),
            fontFamily = PoppinsFontFamily,
            fontSize = 12.sp
        )
    }
}
