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
import java.time.LocalDate


@Composable
fun CalendarRowList(dateSet: MutableList<LocalDate>) {

    var selectedDate by remember { mutableStateOf(dateSet.first()) }

    val pagerState = rememberPagerState(
        pageCount = { dateSet.size / 7 }
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->

        val showingDate = dateSet.chunked(7)

        val chunk = showingDate.get(page)

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

            itemsIndexed(chunk) { _, date ->
                CalendarItem(
                    date = date,
                    isSelected = date == selectedDate,

                    onItemClicked = { selectedDate = date }
                )

            }
        }
    }

}