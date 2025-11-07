package com.example.habitstracker.habit.presentation.today_main.components.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.habitstracker.core.presentation.theme.MyPalette.blueColor
import com.example.habitstracker.habit.domain.ShownHabit
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun CalendarRowList(
    mapDateToHabit: Map<LocalDate, List<ShownHabit>>,
    onDateChangeClick: (newDate: LocalDate) -> Unit,
    selectedDate: LocalDate
) {
    val now = LocalDate.now()
    val firstMonday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    val totalDays = 700

    //val dateSet = generateDateSequence(displayedData, 500)

    // We get a list of dates where today is in the middle
    val dateSet =
        List(2 * totalDays) { num ->
            firstMonday.minusDays(totalDays.toLong()).plusDays(num.toLong())
        }

    val weeks = dateSet.chunked(7)

    // Find the index of the week in which `selectedDate` is now located
    val initialPageIndex = weeks.indexOfFirst { week ->
        selectedDate in week
    }.coerceAtLeast(0) // coerceAtLeast(0) ensures that the value is not less than 0

    val pagerState = rememberPagerState(
        pageCount = { weeks.size },
        initialPage = initialPageIndex
    )

    LaunchedEffect(selectedDate) {
        val newPageIndex = weeks.indexOfFirst { week ->
            selectedDate in week
        }
        pagerState.animateScrollToPage(newPageIndex)
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        val weekDates = weeks.get(page)

        val itemWidthDp = 50.dp
        val itemSpacingDp = 8.dp // якщо є відступи

        val itemWidthPx = with(LocalDensity.current) { itemWidthDp.toPx() }
        val itemSpacingPx = with(LocalDensity.current) { itemSpacingDp.toPx() }
        val totalItemWidth = itemWidthPx + itemSpacingPx

        val lineColor = Color(0xAE105FDC)
        // We calculate the indexes of days where Streak is
        val streakIndexes = weekDates.mapIndexedNotNull { index, date ->
            val habits = mapDateToHabit[date]
            if (isStreakDay(habits)) index else null
        }


        Box(modifier = Modifier.fillMaxWidth()) {
            // 🔵 streak line on LazyRow
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp) // The height of one item
            ) {
                val centerY = size.height / 2
                for (i in 0 until streakIndexes.size - 1) {
                    val cur = streakIndexes[i]
                    val next = streakIndexes[i + 1]
                    if (next == cur + 1) {
                        val startX = cur * totalItemWidth + itemWidthPx / 2
                        val endX = next * totalItemWidth + itemWidthPx / 2
                        drawLine(
                            color = lineColor,
                            start = Offset(startX, centerY),
                            end = Offset(endX, centerY),
                            strokeWidth = 24.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                itemsIndexed(weekDates) { index, date ->
                    val todayHabits = mapDateToHabit[date] ?: emptyList()

                    CalendarItem(
                        todayHabits = todayHabits,
                        date = date,
                        isSelected = date == selectedDate,
                        onItemClicked = { onDateChangeClick(date) },
                    )
                }
            }
        }
    }
}

fun isStreakDay(habits: List<ShownHabit>?): Boolean {
    if (habits.isNullOrEmpty()) return false
    return habits.all { it.isSelected }
}