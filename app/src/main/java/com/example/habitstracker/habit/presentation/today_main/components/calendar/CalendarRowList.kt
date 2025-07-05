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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun CalendarRowList(
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
            itemsIndexed(weekDates) { _, date ->
                CalendarItem(
                    date = date,
                    isSelected = date == selectedDate,
                    onItemClicked = { onDateChangeClick(date) }
                )
            }
        }
    }
}