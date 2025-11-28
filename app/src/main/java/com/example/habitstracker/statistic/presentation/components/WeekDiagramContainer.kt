package com.example.habitstracker.statistic.presentation.components

import android.graphics.Paint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.habitstracker.core.presentation.theme.HabitColor
import com.example.habitstracker.core.presentation.theme.MyPalette
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import com.example.habitstracker.core.presentation.utils.gradientColor
import com.example.habitstracker.habit.domain.DateHabitEntity
import java.time.LocalDate

@Composable
fun WeekDiagramContainer(_weeklyMap: Map<Pair<LocalDate, LocalDate>, List<DateHabitEntity>>) {
    var showAllPreviousWeeks by remember { mutableStateOf(false) }

    // --- filter only those weeks where at least something was done ---
    val weeklyMap = _weeklyMap.filter { (weekRange, habits) ->
        val (monday, _) = weekRange
        val percentageList = buildPercentages(monday, habits)
        percentageList.average() > 0.0
    }

    val today = LocalDate.now()

    // --- divide the weeks ---
    val currentWeek = weeklyMap.filter { (range, _) ->
        val (monday, sunday) = range
        today in monday..sunday
    }

    val previousWeeks = weeklyMap.filterNot { (range, _) ->
        val (monday, sunday) = range
        today in monday..sunday
    }

    // --- sort so that the newer ones go first ---
    val sortedPreviousWeeks =
        previousWeeks.toSortedMap(compareByDescending { it.first })

    // --- limit the number of weeks before the show ---
    val visiblePreviousWeeks = if (showAllPreviousWeeks)
        sortedPreviousWeeks
    else
        sortedPreviousWeeks.entries.take(2).associate { it.key to it.value }

    Column(
        Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        // --- current week ---
        currentWeek.forEach { (weekRange, habits) ->
            val (monday, sunday) = weekRange
            val percentageList = buildPercentages(monday, habits)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Current Week",
                    fontSize = 17.sp,
                    color = Color.White.copy(0.88f),
                    fontFamily = PoppinsFontFamily,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
            WeekAvgCompletion(
                height = 300.dp,
                percentageList = percentageList,
                monday = monday,
                sunday = sunday,
                allHabits = _weeklyMap.values.flatten()
            )
        }

        // --- previous weeks ---
        if (previousWeeks.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Previous Weeks",
                    fontSize = 17.sp,
                    color = Color.White.copy(0.88f),
                    fontFamily = PoppinsFontFamily,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }

            visiblePreviousWeeks.forEach { (weekRange, habits) ->
                val (monday, sunday) = weekRange
                val percentageList = buildPercentages(monday, habits)

                AnimatedVisibility(
                    visible = showAllPreviousWeeks ||
                            visiblePreviousWeeks.entries.indexOfFirst { it.key == weekRange } < 2,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    WeekAvgCompletion(
                        height = 300.dp,
                        percentageList = percentageList,
                        monday = monday,
                        sunday = sunday,
                        allHabits = _weeklyMap.values.flatten()
                    )
                }
            }
        }

        // --- Button "Show more" ---
        if (previousWeeks.size > 2) {
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = gradientColor(
                            HabitColor.DeepBlue.light.copy(0.85f),
                            HabitColor.DeepBlue.dark.copy(0.85f)
                        )
                    )
            ) {
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(200.dp)
                        .height(40.dp),
                    onClick = { showAllPreviousWeeks = !showAllPreviousWeeks },
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (showAllPreviousWeeks) "Hide" else "Show more",
                        color = Color.White.copy(0.88f),
                        fontFamily = PoppinsFontFamily
                    )
                }
            }
        }
    }
}

private fun buildPercentages(monday: LocalDate, habits: List<DateHabitEntity>): List<Float> {
    val today = LocalDate.now()
    val startOfUsage = habits.minOfOrNull { LocalDate.parse(it.currentDate) } ?: today

    return (0..6).map { dayOffset ->
        val currentDay = monday.plusDays(dayOffset.toLong())

        // all habits of this week for a specific day
        val dayHabits = habits.filter { LocalDate.parse(it.currentDate) == currentDay }
        val total = dayHabits.size
        val completed = dayHabits.count { it.completed }

        // if the day after today is 0%
        if (currentDay.isAfter(today)) return@map 0f

        // if the application did not exist yet - we do not take it into account in the average (but show 0)
        if (currentDay.isBefore(startOfUsage)) return@map 0f

        // if there are habits - real value, if not - 0
        if (total > 0) completed.toFloat() / total.toFloat() else 0f
    }
}

@Composable
private fun WeekAvgCompletion(
    height: Dp,
    percentageList: List<Float>,
    allHabits: List<DateHabitEntity>,
    monday: LocalDate,
    sunday: LocalDate
) {
    Card(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerBackgroundDark),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 12.dp)
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

                val avg = calcWeekAvgPercent(monday, allHabits)
                MyText(
                    text = "$avg%", textSize = 17.sp, color = when {
                        avg >= 80 -> Color(0xFF1FE344)
                        avg <= 20 -> Color(0xFFE30909)
                        else -> Color.White
                    }
                )
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
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            WeeklyBars(percentageList = percentageList)
        }
    }
}

private fun calcWeekAvgPercent(monday: LocalDate, habits: List<DateHabitEntity>): Int {
    val today = LocalDate.now()
    val sunday = monday.plusDays(6)
    val startOfUsage = habits.minOfOrNull { LocalDate.parse(it.currentDate) } ?: today

    val from = maxOf(monday, startOfUsage)
    val to = minOf(sunday, today)
    if (to.isBefore(from)) return 0

    val habitsByDate = habits.groupBy { LocalDate.parse(it.currentDate) }

    var total = 0 // count of total habits for this week
    var completed = 0 // count of completed habits for this week
    var d = from
    while (!d.isAfter(to)) {
        val currentDayHabits = habitsByDate[d].orEmpty()
        total += currentDayHabits.size
        completed += currentDayHabits.count { it.completed }
        d = d.plusDays(1)
    }
    val ratio = if (total > 0) completed.toFloat() / total else 0f
    return (ratio * 100).toInt()
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