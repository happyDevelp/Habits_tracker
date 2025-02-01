package com.example.habitstracker.habit.presentation.today_main.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.habitstracker.core.presentation.utils.generateDateSequence
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun CalendarRowList(
    onCurrentDateChange: (newDate: LocalDate) -> Unit
) {
    val displayedData by remember {
        mutableStateOf(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
    }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val dateSet = generateDateSequence(displayedData, 500)

    // Find the index of the week in which `selectedDate` is now located
    val initialPageIndex = dateSet.chunked(7).indexOfFirst { week ->
        selectedDate in week
    }.coerceAtLeast(0) // coerceAtLeast(0) ensures that the value is not less than 0

    val pagerState = rememberPagerState(
        pageCount = { dateSet.size / 7 },
        initialPage = initialPageIndex
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        val weekDates = dateSet.chunked(7).get(page)

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            itemsIndexed(weekDates) { _, date ->
                CalendarItem(
                    date = date,
                    isSelected = date == selectedDate,
                    onItemClicked = {
                        selectedDate = date
                        onCurrentDateChange(selectedDate)
                    }
                )
            }
        }
    }
}