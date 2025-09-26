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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.QuickSandFontFamily
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.utils.TestTags
import com.example.habitstracker.core.presentation.utils.shownHabitExample1
import com.example.habitstracker.core.presentation.utils.shownHabitExample2
import com.example.habitstracker.core.presentation.utils.shownHabitExample3
import com.example.habitstracker.habit.domain.ShownHabit
import com.example.habitstracker.habit.presentation.today_main.components.HabitItem
import com.example.habitstracker.habit.presentation.today_main.components.NotificationDialog
import com.example.habitstracker.habit.presentation.today_main.components.TopBarMainScreen
import com.example.habitstracker.habit.presentation.today_main.components.UnlockedAchievement
import com.example.habitstracker.habit.presentation.today_main.components.calendar.CalendarRowList
import com.example.habitstracker.history.presentation.HistoryViewModel
import com.example.habitstracker.habit.presentation.today_main.components.AchievementMetadata
import com.example.habitstracker.habit.presentation.today_main.utility.getBestStreak
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun TodayScreenRoot(
    viewModel: MainScreenViewModel,
    historyViewModel: HistoryViewModel,
    historyDate: String?,
    changeSelectedItemState: (index: Int) -> Unit
) {
    var isHistoryHandled = false
    LaunchedEffect(historyDate) {
        if (historyDate != null && !isHistoryHandled) {
            viewModel.updateSelectedDate(LocalDate.parse(historyDate))
            isHistoryHandled = true
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val habitListState by viewModel.habitsListState.collectAsStateWithLifecycle()
    val dateState by viewModel.selectedDate.collectAsStateWithLifecycle()
    val mapDateToHabits by viewModel.dateHabitsMap.collectAsStateWithLifecycle()

    val allAchievements = historyViewModel.allAchievements.collectAsStateWithLifecycle().value
    val unlockedAchievement by historyViewModel.unlockedAchievement.collectAsStateWithLifecycle()

    val onDismiss = { historyViewModel.clearUnlockedAchievement() }

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

                // 1. count
                val completedHabits = map.values.flatten().count { it.isSelected }
                val bestStreak = getBestStreak(map)
                val perfectDays =
                    map.values.count { habits -> habits.isNotEmpty() && habits.all { it.isSelected } }

                // 2.Metric dictionary by sections
                val metrics = mapOf(
                    AchievementSection.HABITS_FINISHED to completedHabits,
                    AchievementSection.BEST_STREAK to bestStreak,
                    AchievementSection.PERFECT_DAYS to perfectDays,
                )

                // 3. Find all the achievements that are now done but not notified yet
                val newlyUnlocked = allAchievements.filter { ach ->
                    val section = AchievementSection.fromString(ach.section)
                    !ach.isNotified && (metrics[section] ?: 0) >= ach.target
                }

                // 4.If several are one - choose one (policy: priority by section)
                // Example -Priority: Best Streak -> Perfect Days -> Habits Finished
                fun sectionPriority(section: String) = when (section) {
                    "Best Streak" -> 0
                    "Perfect Days" -> 1
                    "Habits Finished" -> 2
                    else -> 3
                }

                val toShow = newlyUnlocked.sortedWith(
                    compareBy(
                        { sectionPriority(it.section) }, { it.target }
                    )
                ).firstOrNull()

                // If there are new unlocked achievement and they are isNotified then we show a dialogue and update the data
                if (toShow != null) {
                    val today = LocalDate.now().toString()
                    historyViewModel.updateUnlockedDate(today, true, toShow.id)
                    historyViewModel.onAchievementUnlocked(
                        UnlockedAchievement(
                            iconRes = AchievementMetadata.icons(toShow.section, context),
                            target = toShow.target,
                            description = AchievementMetadata.description(toShow, context),
                            textPadding = AchievementMetadata.textPadding(toShow.section, context)
                        )
                    )
                }
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

    TodayScreen(
        habitListState = habitListState,
        dateState = dateState,
        mapDateToHabits = mapDateToHabits,
        unlockedAchievement = unlockedAchievement,
        onSelectClick = onSelectClick,
        onDeleteClick = onDeleteClick,
        onDateChangeClick = onDateChangeClick,
        changeSelectedItemState = changeSelectedItemState,
        onDismiss = onDismiss,
    )
}

@Composable
fun TodayScreen(
    modifier: Modifier = Modifier,
    habitListState: List<ShownHabit>,
    dateState: LocalDate,
    mapDateToHabits: Map<LocalDate, List<ShownHabit>>,
    unlockedAchievement: UnlockedAchievement?,
    onDismiss: () -> Unit,
    onSelectClick: (id: Int, isDone: Boolean, selectDate: String) -> Unit,
    onDeleteClick: (id: Int) -> Unit,
    onDateChangeClick: (newDate: LocalDate) -> Unit,
    changeSelectedItemState: (index: Int) -> Unit
) {
    val navController = LocalNavController.current

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(topBar = { TopBarMainScreen(modifier, navController) }) { paddingValues ->
            Card(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                colors = CardDefaults.cardColors(
                    containerColor = screenBackgroundDark,
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
                            mapDateToHabit = mapDateToHabits,
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
                                                    .padding(
                                                        top = 8.dp,
                                                        start = 20.dp,
                                                        bottom = 12.dp
                                                    ),
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
    if (unlockedAchievement != null) {
        NotificationDialog(
            unlockedAchievement = unlockedAchievement,
            onDismiss = onDismiss,
            changeSelectedItemState = changeSelectedItemState
        )
    }
}

enum class AchievementSection {
    HABITS_FINISHED, BEST_STREAK, PERFECT_DAYS;

    companion object {
        fun fromString(section: String) = when (section) {
            "Habits Finished" -> HABITS_FINISHED
            "Best Streak" -> BEST_STREAK
            "Perfect Days" -> PERFECT_DAYS
            else -> throw IllegalArgumentException("Invalid section: $section")
        }
    }
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
                mapDateToHabits = emptyMap(),
                onDismiss = {},
                changeSelectedItemState = { },
                unlockedAchievement = UnlockedAchievement(
                    R.drawable.streak_achiev, 100, "Finish 100 Habits",
                    textPadding = 0.dp
                ),
            )
        }
    }
}