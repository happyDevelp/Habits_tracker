package com.example.habitstracker.ui.screens.history.tab_screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.ui.custom.MyText
import com.example.habitstracker.ui.screens.history.components.calendar.CalendarItem
import com.example.habitstracker.ui.screens.history.components.calendar.SpacerItem
import com.example.habitstracker.ui.screens.history.components.calendar.TopPanel
import com.example.habitstracker.ui.screens.history.components.statistic_containers.CustomBlank
import com.example.habitstracker.ui.screens.history.components.statistic_containers.getFilledBlankList
import com.example.habitstracker.ui.theme.screenContainerBackgroundDark
import com.example.habitstracker.ui.theme.screensBackgroundDark
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun HistoryCalendarScreenPreview(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(screensBackgroundDark)
    )
    { HistoryCalendarScreen() }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryCalendarScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var currentDate by remember {
        mutableStateOf(LocalDate.now().withDayOfMonth(1))
    }

    val pagerState = rememberPagerState(
        initialPage = currentDate.monthValue,
        /** Must be as many month as the user use the app **/
        pageCount = { LocalDate.now().monthValue + 1 }
    )

    LazyColumnContainer {

        /** Statistic containers **/
        DrawStatisticContainers(modifier, context)

        Spacer(modifier = modifier.height(12.dp))

        /** Calendar **/
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .height(355.dp),
            colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark),
        ) {

            Column(
                modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TopPanel(
                    currentDate = currentDate,
                    minusMonth = {
                        coroutineScope.launch {
                            currentDate = currentDate.minusMonths(1)
                            val currentMonth = currentDate.monthValue
                            pagerState.animateScrollToPage(currentMonth)
                        }
                    },
                    plusMonth = {
                        coroutineScope.launch {
                            currentDate = currentDate.plusMonths(1)
                            pagerState.animateScrollToPage(currentDate.monthValue)
                        }
                    }
                )

                Spacer(modifier = modifier.height(4.dp))

                val lengthOfMonth = currentDate.lengthOfMonth()
                val daysOfWeek = getDaysOfWeek()

                // List that will be displayed on the screen
                val displayedCalendarList = mutableListOf<Pair<String, String>>()
                addDaysOfWeek(daysOfWeek, displayedCalendarList)

                val firstDayOfWeek = currentDate.withDayOfMonth(1).dayOfWeek
                val spacerDays = firstDayOfWeek.ordinal

                addSpacerDays(spacerDays, displayedCalendarList)

                addDaysOfMonth(lengthOfMonth, displayedCalendarList, currentDate)

                //parameter means, if value is changed, do this code
                LaunchedEffect(pagerState.currentPage) {
                    currentDate = currentDate.withMonth(pagerState.currentPage)
                }

                // HorizontalPager to scroll between month
                CalendarHorizontalPager(pagerState, modifier, displayedCalendarList)
            }
        }

        StatisticSection()

    }
}

@Composable
private fun DrawStatisticContainers(modifier: Modifier, context: Context) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {

        val statsList = getFilledBlankList(context = context)

        statsList.forEach { blankItem ->
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
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun StatisticSection(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(24.dp))

    MyText(
        modifier = modifier.padding(start = 16.dp),
        text = "STATISTICS",
        textSize = 18.sp
    )

    val formatter = DateTimeFormatter.ofPattern(
        stringResource(id = R.string.month_and_year_pattern),
        Locale.getDefault()
    )

    val formattedCurrentDate = LocalDate.now().format(formatter)
    MyText(
        modifier = modifier.padding(start = 16.dp, top = 6.dp),
        text = formattedCurrentDate,
        textSize = 14.sp,
        color = Color.White.copy(0.7f)
    )

    CustomStatisticContainer(height = 280.dp)
}

@Composable
private fun CalendarHorizontalPager(
    pagerState: PagerState,
    modifier: Modifier,
    displayedCalendarList: MutableList<Pair<String, String>>,
) {
    HorizontalPager(
        state = pagerState
    ) { page ->

        LazyVerticalGrid(
            modifier = modifier
                .wrapContentSize()
                .padding(horizontal = 4.dp),
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            displayedCalendarList.forEach { (numOfDay, _) ->
                item {
                    if (numOfDay.isNotEmpty())
                        CalendarItem(day = numOfDay)
                    else SpacerItem()
                }
            }
        }
    }
}

/** Add days of month to [displayedCalendarList] **/
@RequiresApi(Build.VERSION_CODES.O)
fun addDaysOfMonth(
    lengthOfMonth: Int,
    displayedCalendarList: MutableList<Pair<String, String>>,
    currentDate: LocalDate,
) {
    for (day in 1..lengthOfMonth) {
        val currentDay = day.toString()
        val currentDayOfWeek = currentDate.withDayOfMonth(day).dayOfWeek.toString()

        displayedCalendarList.add(Pair(currentDay, currentDayOfWeek))
    }
}

/** Add names of days of the week (Mon, Tue, Wed,...) to [displayedCalendarList] **/
fun addDaysOfWeek(
    daysOfWeek: MutableList<Pair<String, String>>,
    displayedCalendarList: MutableList<Pair<String, String>>,
) {
    daysOfWeek.forEach { day ->
        displayedCalendarList.add(Pair(day.first, day.second))
    }
}

/** Add spacer to [displayedCalendarList]
 * For example if first day of month is Wed, then we should to add a spacer for Mon and Tue **/
fun addSpacerDays(emptyDays: Int, displayedCalendarList: MutableList<Pair<String, String>>) {
    for (i in 1..emptyDays) {
        displayedCalendarList.add(Pair("", ""))
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

@Composable
fun CustomStatisticContainer(modifier: Modifier = Modifier, height: Dp) {
    Card(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark),
    ) {
        Row(
            modifier = modifier
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyText(text = "Sep 15 - 21", textSize = 17.sp)
            MyText(text = "91%", textSize = 15.sp)
        }

        MyText(
            modifier = modifier.padding(start = 70.dp, top = 100.dp),
            text = "TODO execution diagram",
            textSize = 15.sp
        )
    }
}

@Composable
fun LazyColumnContainer(content: @Composable () -> Unit) {
    LazyColumn { item { content.invoke() } }
}