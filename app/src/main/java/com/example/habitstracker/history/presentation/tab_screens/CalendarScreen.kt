package com.example.habitstracker.history.presentation.tab_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.presentation.components.StatisticSection
import com.example.habitstracker.history.presentation.components.calendar.HistoryCalendarDay
import com.example.habitstracker.history.presentation.components.calendar.TopPanel
import com.example.habitstracker.history.presentation.components.statistic_containers.CustomBlank
import com.example.habitstracker.history.presentation.components.statistic_containers.getFilledBlankList
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun HistoryCalendarScreen(
    modifier: Modifier = Modifier,
    streakList: List<DateHabitEntity>,
    changeSelectedItemState: (index: Int) -> Unit,
    mapDateToHabits: Map<LocalDate, List<DateHabitEntity>>
) {
    val monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val week = (0..6).map { monday.plusDays(it.toLong()) }

    val completedPercentage = week.map { date ->
        val habits = streakList.filter { LocalDate.parse(it.currentDate) == date }
        if (habits.isEmpty()) 0f
        else habits.count { it.isCompleted }.toFloat() / habits.size.toFloat()
    }

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

                //parameter means, if value is changed, do this code
                LaunchedEffect(pagerState.currentPage) {
                    currentDate = currentDate.withMonth(pagerState.currentPage)
                }

                // HorizontalPager to scroll between month
                CalendarHorizontalPager(
                    pagerState = pagerState,
                    modifier = modifier,
                    currentDate = currentDate,
                    mapDateToHabits = mapDateToHabits,
                    changeSelectedItemState = changeSelectedItemState
                )
            }
        }

        StatisticSection(completedPercentageList = completedPercentage)
    }
}

@Composable
private fun DrawStatisticContainers(streakList: List<DateHabitEntity>) {
    val context = LocalContext.current

    /** current streak and the best streak*/
    val currentStreak = getCurrentStreak(streakList)
    val bestStreak = getBestStreak(streakList)

    /** total completed habits  */
    val totalCompletedHabits = streakList.count { it.isCompleted }


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
    val percentage = totalCompletedHabits / totalHabits.toFloat() * 100

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
                totalCompletedHabits = totalCompletedHabits,
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

private fun buildMonthGrid(yearMonth: LocalDate): List<LocalDate?> {
    val firstOfMonth = yearMonth.withDayOfMonth(1)
    val length = yearMonth.lengthOfMonth()
    val firstDow = firstOfMonth.dayOfWeek.value
    //val leadingNulls = if(firstDow == 7) 0 else firstDow - 1
    val leadingNulls = (firstDow + 6) % 7  // 0..6, Sunday -> 6

    val days = (1..length).map { day ->
        firstOfMonth.withDayOfMonth(day)
    }
    return List(leadingNulls) { null } + days
}

private fun isPerfectDay(list: List<DateHabitEntity>?): Boolean =
    list?.isNotEmpty() == true && list.all { it.isCompleted }

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun WeekRow(
    week: List<LocalDate?>,
    mapDateToHabits: Map<LocalDate, List<DateHabitEntity>>,
    onDateClick: (LocalDate) -> Unit,
) {
    val cellSize = 40.dp
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(cellSize)
    ) {
        // Gap between 7 columns to even get into a row
        val cellGap = remember(maxWidth) {
            val raw = (maxWidth - cellSize * 7) / 6
            if (raw < 0.dp) 0.dp else raw
        }

        // ==== Background: Stroke line ====
        Canvas(modifier = Modifier.matchParentSize()) {
            val itemW = cellSize.toPx()
            val gap = cellGap.toPx()
            val step = itemW + gap
            val centerY = size.height / 2f

            val perfectIdx = week.mapIndexedNotNull { idx, date ->
                if (date != null && isPerfectDay(mapDateToHabits[date])) idx else null
            }

            for (i in 0 until perfectIdx.size - 1) {
                val cur = perfectIdx[i]
                val next = perfectIdx[i + 1]
                if (next == cur + 1) {
                    val startX = cur * step + itemW / 2f
                    val endX = next * step + itemW / 2f
                    drawLine(
                        color = Color(0xAE105FDC),
                        start = Offset(startX, centerY),
                        end = Offset(endX, centerY),
                        strokeWidth = 20.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        Row(
            modifier = Modifier.matchParentSize(),
            horizontalArrangement = Arrangement.spacedBy(cellGap),
            verticalAlignment = Alignment.CenterVertically
        ) {
            week.forEach { date ->
                val habits = if (date == null) emptyList()
                else (mapDateToHabits[date] ?: emptyList())

                Box(
                    modifier = Modifier
                        .width(cellSize)
                        .height(cellSize),
                    contentAlignment = Alignment.Center
                ) {
                    HistoryCalendarDay(
                        date = date,
                        habits = habits,
                        onClick = onDateClick
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun WeekdayHeader() {
    val cellSize = 40.dp
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val cellGap = remember(maxWidth) {
            val raw = (maxWidth - cellSize * 7) / 6
            if (raw < 0.dp) 0.dp else raw
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(cellSize),
            horizontalArrangement = Arrangement.spacedBy(cellGap),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                Box(
                    modifier = Modifier
                        .width(cellSize)
                        .height(cellSize),
                    contentAlignment = Alignment.Center
                ) {
                    MyText(text = day, textSize = 14.sp)
                }
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

fun getBestStreak(streakList: List<DateHabitEntity>): Int {
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
private fun CalendarHorizontalPager(
    pagerState: PagerState,
    modifier: Modifier,
    currentDate: LocalDate,
    mapDateToHabits: Map<LocalDate, List<DateHabitEntity>>,
    changeSelectedItemState: (index: Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        WeekdayHeader()
        Spacer(modifier = modifier.height(4.dp))

        HorizontalPager(state = pagerState) { page ->
            val ym = currentDate.withMonth(page)
            val grid = buildMonthGrid(ym)
            val tail = (7 - (grid.size % 7)) % 7
            val weeks = (grid + List(tail) { null }).chunked(7)

            Column(modifier = Modifier.fillMaxWidth()) {
                val nav = LocalNavController.current

                weeks.forEach { week ->
                    WeekRow(
                        week = week,
                        mapDateToHabits = mapDateToHabits,
                        onDateClick = { d ->
                            changeSelectedItemState(0)
                            nav.popBackStack()
                            nav.currentBackStackEntry?.savedStateHandle?.set(
                                "current_date",
                                d.toString()
                            )
                        }
                    )
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
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
                .background(screenBackgroundDark)
        )
        {
            HistoryCalendarScreen(
                changeSelectedItemState = {},
                streakList = listOf(),
                mapDateToHabits = emptyMap()
            )
        }
    }
}