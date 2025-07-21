package com.example.habitstracker.history.presentation.tab_screens

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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screensBackgroundDark
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.presentation.components.calendar.CalendarItem
import com.example.habitstracker.history.presentation.components.calendar.SpacerItem
import com.example.habitstracker.history.presentation.components.calendar.TopPanel
import com.example.habitstracker.history.presentation.components.statistic_containers.CustomBlank
import com.example.habitstracker.history.presentation.components.statistic_containers.getFilledBlankList
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.util.Locale


@Composable
fun HistoryCalendarScreen(
    modifier: Modifier = Modifier,
    streakList: List<DateHabitEntity>,
    changeSelectedItemState: (index: Int) -> Unit
) {
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
        DrawStatisticContainers(streakList = streakList)
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

                // List that will be displayed on the screen
                val displayedCalendarList = mutableListOf<Pair<String, String>>()

                val firstDayOfWeek = currentDate.withDayOfMonth(1).dayOfWeek
                val spacerDays = firstDayOfWeek.ordinal

                addSpacerDays(spacerDays, displayedCalendarList)

                addDaysOfMonth(lengthOfMonth, displayedCalendarList, currentDate)

                //parameter means, if value is changed, do this code
                LaunchedEffect(pagerState.currentPage) {
                    currentDate = currentDate.withMonth(pagerState.currentPage)
                }

                // HorizontalPager to scroll between month
                CalendarHorizontalPager(
                    pagerState,
                    modifier,
                    displayedCalendarList,
                    currentDate,
                    changeSelectedItemState
                )
            }
        }

        StatisticSection()
    }
}

@Composable
private fun DrawStatisticContainers(streakList: List<DateHabitEntity>) {
    val context = LocalContext.current

    /** current streak and the best streak*/
    val currentStreak = getCurrentStreak(streakList)
    val bestStreak=getBestStreak(streakList)

    /** total completed habits  */
    val completedHabitsCount = streakList.count { it.isCompleted }


    /** total completed habits this week */
    val currentDate = LocalDate.now()
    val lastMonday =
        remember(currentDate) {
            currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        }
    val thisWeekSelectedHabits = streakList
        .filter { it.currentDate >= lastMonday.toString() }
        .count { it.isCompleted }


    /** Percentage of completed habits */
    val totalHabits = streakList.size
    val percentage = completedHabitsCount / totalHabits.toFloat() * 100

    /** Perfect days */
    val perfectDaysCounter by remember {
        mutableIntStateOf(
            streakList
                .groupBy { it.currentDate }
                .count { (_, habits) -> habits.all { it.isCompleted } }
        )
    }

    val thisWeekPerfectedDays by remember {
        mutableIntStateOf(
            streakList
                .filter { it.currentDate >= lastMonday.toString() }
                .groupBy { it.currentDate }
                .count { (_, habits) -> habits.all { it.isCompleted } }
        )
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        val statsList =
            getFilledBlankList(
                context = context,
                currentStreak = currentStreak,
                bestStreak = bestStreak,
                completedHabitsCount = completedHabitsCount,
                thisWeekSelectedHabits = thisWeekSelectedHabits,
                totalHabits = totalHabits,
                percentage = percentage,
                perfectDaysCounter = perfectDaysCounter,
                thisWeekPerfectedDays = thisWeekPerfectedDays
            )

        statsList.forEach { blankItem ->
            item {
                CustomBlank(
                    color = blankItem.color,
                    topText = blankItem.topText,
                    middleText = blankItem.middleText,
                    bottomText = blankItem.bottomText,
                )
            }
        }
    }
}

private fun getCurrentStreak(streakList: List<DateHabitEntity>): Int {
    if (streakList.isEmpty()) return 0

    var streak = 0
    val groupedStreakList = streakList.groupBy { it.currentDate }

    groupedStreakList.forEach { mapDateAndHabits ->
        val habitsCount = mapDateAndHabits.value.count()
        val isCompletedCount = mapDateAndHabits.value.count { it.isCompleted }
        if (habitsCount == isCompletedCount)
            streak++
        else return streak
    }

    return streak
}

private fun getBestStreak(streakList: List<DateHabitEntity>): Int {
    if (streakList.isEmpty()) return 0

    // Group all habit entries by their date
    val mapDateToHabits = streakList.groupBy { it.currentDate }

    // Sort dates descending (newest first)
    val sortedDates = mapDateToHabits.keys.sorted()

    var bestStreak = 0
    var currentStreak = 0
    var previousDate: LocalDate? = null

    for (date in sortedDates) {
        val habitsForDate = mapDateToHabits[date].orEmpty()
        val total = habitsForDate.size
        val completed = habitsForDate.count { it.isCompleted }

        val allCompleted = (total == completed)

        if (allCompleted) {
            if (previousDate == null || previousDate.plusDays(1) == LocalDate.parse(date)) {
                currentStreak++
            } else {
                currentStreak = 1
            }
            bestStreak = maxOf(bestStreak, currentStreak)
        } else {
            currentStreak = 0
        }

        previousDate = LocalDate.parse(date)
    }

    return bestStreak
}

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
    currentDate: LocalDate,
    changeSelectedItemState: (index: Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val list = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            list.forEach { day ->
                MyText(
                    text = day,
                    textSize = 15.sp,
                )
            }
        }
        Spacer(modifier = modifier.height(16.dp))

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
                        val dayInt = numOfDay.toIntOrNull()
                        if (dayInt != null) {
                            val date = currentDate.withDayOfMonth(numOfDay.toInt())
                            CalendarItem(
                                date = date,
                                changeSelectedItemState = changeSelectedItemState
                            )
                        } else SpacerItem()
                    }
                }
            }
        }
    }
}

/** Add days of month to [displayedCalendarList] **/
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

/** Add spacer to [displayedCalendarList]
 * For example if first day of month is Wed, then we should to add a spacer for Mon and Tue **/
fun addSpacerDays(emptyDays: Int, displayedCalendarList: MutableList<Pair<String, String>>) {
    for (i in 1..emptyDays) {
        displayedCalendarList.add(Pair("", ""))
    }
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

@Preview(showSystemUi = true)
@Composable
fun HistoryCalendarScreenPreview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(screensBackgroundDark)
        )
        { HistoryCalendarScreen(changeSelectedItemState = {}, streakList = listOf()) }
    }
}