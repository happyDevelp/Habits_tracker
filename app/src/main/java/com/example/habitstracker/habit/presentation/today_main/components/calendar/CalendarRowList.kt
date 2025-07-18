package com.example.habitstracker.habit.presentation.today_main.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
        selectedDate  in week
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

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            itemsIndexed(weekDates) { index, date ->
                val todayHabits = mapDateToHabit[date] ?: emptyList()
                val isStreakDay = isStreakDay(todayHabits)
                val isPrevStreak = weekDates.getOrNull(index - 1)
                    ?.let { isStreakDay(mapDateToHabit[it]) } ?: false
                val isNextStreak  = weekDates.getOrNull(index + 1)
                    ?.let { isStreakDay(mapDateToHabit[it]) } ?: false

                CalendarItem(
                    todayHabits = todayHabits,
                    date = date,
                    isSelected = date == selectedDate,
                    onItemClicked = { onDateChangeClick(date) },
                    isStreakDay = isStreakDay,
                    isPrevStreak = isPrevStreak,
                    isNextStreak = isNextStreak
                )
            }
        }
    }
}

fun isStreakDay(habits: List<ShownHabit>?): Boolean {
    if (habits.isNullOrEmpty()) return false
    return habits.all { it.isSelected }
}