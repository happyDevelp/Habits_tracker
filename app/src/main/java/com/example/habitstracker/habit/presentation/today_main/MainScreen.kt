package com.example.habitstracker.habit.presentation.today_main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.QuickSandFontFamily
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screensBackgroundDark
import com.example.habitstracker.core.presentation.utils.TestTags
import com.example.habitstracker.core.presentation.utils.shownHabitExample1
import com.example.habitstracker.core.presentation.utils.shownHabitExample2
import com.example.habitstracker.core.presentation.utils.shownHabitExample3
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.ShownHabit
import com.example.habitstracker.habit.presentation.today_main.components.HabitItem
import com.example.habitstracker.habit.presentation.today_main.components.TopBarMainScreen
import com.example.habitstracker.habit.presentation.today_main.components.calendar.CalendarRowList
import com.example.habitstracker.history.domain.StatisticEntity
import com.example.habitstracker.history.presentation.HistoryViewModel
import com.example.habitstracker.history.presentation.tab_screens.getBestStreak
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun TodayScreenRoot(
    viewModel: MainScreenViewModel,
    historyViewModel: HistoryViewModel,
    historyDate: String?
) {
    val coroutineScope = rememberCoroutineScope()
    val statistic by historyViewModel.statistic.collectAsStateWithLifecycle() // поточна статистика з БД

    var newRecordMessage by remember { mutableStateOf<String?>(null) } // локальний тригер діалогу

    var isHistoryHandled: Boolean = false
    LaunchedEffect(historyDate) {
        if (historyDate != null && !isHistoryHandled) {
            viewModel.updateSelectedDate(LocalDate.parse(historyDate))
            isHistoryHandled = true
        }
    }
    val habitListState by viewModel.habitsListState.collectAsStateWithLifecycle()
    val streakList by historyViewModel.dateHabitList.collectAsStateWithLifecycle()
    val dateState by viewModel.selectedDate.collectAsStateWithLifecycle()


    val onSelectClick: (id: Int, isDone: Boolean, selectDate: String) -> Unit =
        { id, isDone, selectDate ->
            coroutineScope.launch {
                viewModel.updateDateSelectState(id, isDone, selectDate)

                val map = viewModel.dateHabitsMap.value
                    .toMutableMap().apply {
                        val habitsForDate = getOrDefault(LocalDate.parse(selectDate), emptyList())
                        val updatedHabits = habitsForDate.map {
                            if (it.id == id) it.copy(isSelected = isDone) else it
                        }
                        this[LocalDate.parse(selectDate)] = updatedHabits
                    }

                val completedHabits = map.values.flatten().count { it.isSelected }
                val bestStreak = getBestStreak(map)
                val perfectDays =
                    map.values.count { habits -> habits.isNotEmpty() && habits.all { it.isSelected } }

                // 3) перевірити рекорд **до** запису в БД (і лише у відповідь на дію),
                //    встановлюємо message тільки якщо ще не показаний
                if (newRecordMessage == null) {
                    // пріоритет вибирай сам: тут — спочатку streak, потім perfect, потім completed
                    when {
                        bestStreak > statistic.bestStreak ->
                            newRecordMessage = "Найкращий стрік: $bestStreak"
                        perfectDays > statistic.perfectDays ->
                            newRecordMessage = "Perfect days: $perfectDays"
                        completedHabits > statistic.completedHabits ->
                            newRecordMessage = "Total completed habits: $completedHabits"
                    }
                }

                // 4) оновити БД зі статистикою (після того, як вирішили показати діалог)
                historyViewModel.updateStatistic(completedHabits, bestStreak, perfectDays)
            }
        }

    val onDeleteClick: (id: Int) -> Unit = { habitId ->
        coroutineScope.launch {
            viewModel.deleteHabit(habitId)
        }
    }

    val onDateChangeClick: (newDate: LocalDate) -> Unit = { newDate ->
        viewModel.updateSelectedDate(newDate)
    }

    val mapDateToHabit by viewModel.dateHabitsMap.collectAsStateWithLifecycle()

    TodayScreen(
        habitListState = habitListState,
        dateState = dateState,
        mapDateToHabit = mapDateToHabit, // може прибрати?
        //streakList = streakList,
        statistic = statistic,
        newRecordMessage = newRecordMessage,
        onDismissNewRecord = { newRecordMessage = null },
        onSelectClick = onSelectClick,
        onDeleteClick = onDeleteClick,
        onDateChangeClick = onDateChangeClick
    )
}

@Composable
fun TodayScreen(
    modifier: Modifier = Modifier,
    habitListState: List<ShownHabit>,
    dateState: LocalDate,
    statistic: StatisticEntity,
    newRecordMessage: String?,
    onDismissNewRecord: () -> Unit,
    mapDateToHabit: Map<LocalDate, List<ShownHabit>>,
    onSelectClick: (id: Int, isDone: Boolean, selectDate: String) -> Unit,
    onDeleteClick: (id: Int) -> Unit,
    onDateChangeClick: (newDate: LocalDate) -> Unit
) {

    val navController = LocalNavController.current
// показ діалогу лише коли newRecordMessage не null
    if (newRecordMessage != null) {
        Dialog(onDismissRequest = onDismissNewRecord) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark)
            ) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = newRecordMessage,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onDismissNewRecord, colors = ButtonDefaults.buttonColors(containerColor = blueColor)) {
                        Text("CLOSE", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    Scaffold(topBar = { TopBarMainScreen(modifier, navController) }) { paddingValues ->
        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            colors = CardDefaults.cardColors(
                containerColor = screensBackgroundDark,
            ),
            shape = RoundedCornerShape(topStart = 27.dp, topEnd = 27.dp)
        )
        {
            Box(
                modifier = modifier
                    .padding(top = 20.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val weekDays = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        weekDays.forEach { day ->
                            Text(
                                text = day,
                                fontSize = 13.sp,
                                color = Color.White,
                                fontFamily = QuickSandFontFamily,
                            )
                        }
                    }

                    CalendarRowList(
                        mapDateToHabit = mapDateToHabit,
                        onDateChangeClick = { newDate ->
                            onDateChangeClick(newDate)
                        },
                        selectedDate = dateState
                    )

                    val groupedHabits = habitListState.groupBy { it.executionTime }

                    val dayPartsOrder = listOf("Anytime", "Morning", "Day", "Evening")

                    Crossfade(
                        targetState = groupedHabits,
                        label = "Data change animation"
                    ) { habits ->
                        LazyColumn(
                            modifier = modifier
                                .padding(top = 20.dp)
                                .fillMaxHeight(1f)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            dayPartsOrder.forEach { dayPart ->
                                val habitsInPart = habits[dayPart].orEmpty()
                                if (habitsInPart.isNotEmpty()) {
                                    item {
                                        Text(
                                            modifier = modifier
                                                .fillMaxWidth()
                                                .padding(top = 8.dp, start = 20.dp, bottom = 12.dp),
                                            text = dayPart,
                                            color = Color.White.copy(alpha = 0.8f),
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = PoppinsFontFamily,
                                        )
                                    }


                                    items(habitsInPart) { habit ->
                                        HabitItem(
                                            shownHabit = habit,
                                            currentDate = dateState,
                                            onSelectClick = onSelectClick,
                                            onDeleteClick = onDeleteClick
                                        )
                                        Spacer(modifier = modifier.height(20.dp))
                                    }
                                }
                            }

                            item {
                                Spacer(modifier = modifier.height(6.dp))

                                if (dateState == LocalDate.now()) {
                                    Button(
                                        modifier = modifier
                                            .padding(bottom = 20.dp)
                                            .fillMaxWidth(0.7f)
                                            .height(50.dp)
                                            .testTag(TestTags.CREATE_NEW_HABIT_BUTTON),

                                        onClick = {
                                            navController.navigate(Route.AddHabit)
                                        },

                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = screenContainerBackgroundDark,
                                            contentColor = Color.White.copy(alpha = 0.75f)
                                        ),

                                        shape = RoundedCornerShape(10.dp),

                                        elevation = ButtonDefaults.buttonElevation(
                                            defaultElevation = 6.dp,
                                            pressedElevation = 16.dp
                                        ),

                                        ) {
                                        Text(
                                            text = stringResource(R.string.create_a_new_habit),
                                            modifier = modifier.padding(horizontal = 8.dp),
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = PoppinsFontFamily,
                                            color = Color.White,
                                        )
                                    }
                                }
                            }
                        }
                    }


                }

            }
        }
    }
}

fun getBestStreak(mapHabitsToDate: Map<LocalDate, List<ShownHabit>>): Int {
    val dates = mapHabitsToDate.keys.toList().sorted()
    var currentStreak = 0
    var bestStreak = 0

    for (date in dates) {
        val habits = mapHabitsToDate[date].orEmpty()
        val isPerfectDay = habits.isNotEmpty() && habits.all { it.isSelected }

        if (isPerfectDay) {
            currentStreak++
            bestStreak = maxOf(bestStreak, currentStreak)
        } else {
            currentStreak = 0
        }
    }
    return bestStreak
}

@Composable
@Preview(showSystemUi = false)
private fun Preview() {
    val mockNavController = rememberNavController()
    val mockList = listOf(
        shownHabitExample1,
        shownHabitExample2,
        shownHabitExample3
    )

    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            TodayScreen(
                habitListState = mockList,
                onSelectClick = { _, _, _ -> },
                onDeleteClick = { _ -> },
                onDateChangeClick = {},
                dateState = LocalDate.now(),
                mapDateToHabit = emptyMap(),
                //streakList = emptyList(),
                statistic = StatisticEntity(0,0,0,0),
                newRecordMessage = null,
                onDismissNewRecord = {}
            )
        }
    }
}