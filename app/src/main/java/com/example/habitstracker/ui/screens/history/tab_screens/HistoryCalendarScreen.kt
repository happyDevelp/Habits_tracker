package com.example.habitstracker.ui.screens.history.tab_screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habitstracker.ui.screens.history.components.calendar.CalendarItem
import com.example.habitstracker.ui.screens.history.components.calendar.SpacerItem
import com.example.habitstracker.ui.screens.history.components.calendar.TopPanel
import com.example.habitstracker.ui.screens.history.components.statistic_containers.CustomBlank
import com.example.habitstracker.ui.screens.history.components.statistic_containers.getFilledBlankList
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.screenContainerBackgroundDark
import com.example.habitstracker.ui.theme.screensBackgroundDark
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun HistoryCalendarScreenPreview(modifier: Modifier = Modifier) {
    AppTheme(darkTheme = true) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(screensBackgroundDark)
        ) {
            HistoryCalendarScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryCalendarScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize(),
    ) {

        /** Statistic containers **/
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {

            val statsList = getFilledBlankList(context = context)

            statsList.forEachIndexed { index, blankItem ->
                item {
                    CustomBlank(
                        color = blankItem.color,
                        topText = blankItem.topText,
                        middleText = blankItem.middleText,
                        bottomText = blankItem.bottomText
                    )
                }
            }
        }

        Spacer(modifier = modifier.height(12.dp))


        /** Calendar **/

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .height(310.dp),
            colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark),
        ) {

            var currentDate by remember {
                mutableStateOf(LocalDate.now())
            }

            Column(
                modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopPanel(
                    currentDate = currentDate,
                    minusMonth = { currentDate = currentDate.minusMonths(1) },
                    plusMonth = { currentDate = currentDate.plusMonths(1) }
                )

                Spacer(modifier = modifier.height(8.dp))

                val previousMonth = currentDate.minusMonths(1)
                val nextMonth = currentDate.plusMonths(1)

                val lengthOfMonth = currentDate.lengthOfMonth()

                val daysOfWeek = getDaysOfWeek()

                val calendarDaysList = mutableListOf<Pair<String, String>>()
                daysOfWeek.forEach { day ->
                    calendarDaysList.add(Pair(day.first, day.second))
                }

                val firstDayOfWeek = currentDate.withDayOfMonth(1).dayOfWeek
                val emptyDays = firstDayOfWeek.ordinal

                for (i in 1..emptyDays) {
                    calendarDaysList.add(Pair("", ""))
                }

                for (day in 1..lengthOfMonth) {
                    val currentDay = day.toString()
                    val currentDayOfWeek =
                        currentDate.withDayOfMonth(day).dayOfWeek.toString()
                    calendarDaysList.add(Pair(currentDay, currentDayOfWeek))
                }

                LazyVerticalGrid(
                    modifier = modifier
                        .wrapContentSize()
                        .padding(horizontal = 4.dp),
                    columns = GridCells.Fixed(7),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    calendarDaysList.forEach { (numOfDay, dayOfWeek) ->
                        item {
                            if (numOfDay.isNotEmpty())
                                CalendarItem(day = numOfDay)
                            else SpacerItem()
                        }
                    }

                }

            }

        }
    }
}

fun getDaysOfWeek(): MutableList<Pair<String, String>> {
    return mutableListOf(
        Pair("Mon", ""),
        Pair("Tue", ""),
        Pair("Wed", ""),
        Pair("Thu", ""),
        Pair("Fri", ""),
        Pair("Sat", ""),
        Pair("Sun", "")
    )
}