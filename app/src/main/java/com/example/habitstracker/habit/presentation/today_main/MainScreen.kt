package com.example.habitstracker.habit.presentation.today_main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
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
import com.example.habitstracker.core.presentation.theme.BoldFontFamily
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.QuickSandFontFamily
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.utils.TestTags
import com.example.habitstracker.core.presentation.utils.clickWithRipple
import com.example.habitstracker.core.presentation.utils.shownHabitExample1
import com.example.habitstracker.core.presentation.utils.shownHabitExample2
import com.example.habitstracker.core.presentation.utils.shownHabitExample3
import com.example.habitstracker.habit.domain.ShownHabit
import com.example.habitstracker.habit.presentation.today_main.components.AchievementMetadata
import com.example.habitstracker.habit.presentation.today_main.components.HabitItem
import com.example.habitstracker.habit.presentation.today_main.components.TopBarMainScreen
import com.example.habitstracker.habit.presentation.today_main.components.UnlockedAchievement
import com.example.habitstracker.habit.presentation.today_main.components.calendar.CalendarRowList
import com.example.habitstracker.history.presentation.HistoryViewModel
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

    if (unlockedAchievement != null) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF2196F3), Color(0xFF0D47A1)),
                                start = Offset(0f, 0f),
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            ),
                            shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp),
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Congratulations!",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(150.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = unlockedAchievement.iconRes),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(150.dp)
                        )
                        Text(
                            text = unlockedAchievement.target.toString(),
                            fontSize = 20.sp,
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = blueColor,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = unlockedAchievement.textPadding)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = stringResource(R.string.new_achievement),
                        fontSize = 20.sp,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = unlockedAchievement.description,
                        fontSize = 14.sp,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White.copy(0.8f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(26.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(55.dp),
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = "CLOSE",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = stringResource(R.string.my_achievements),
                        fontSize = 14.sp,
                        fontFamily = BoldFontFamily,
                        fontWeight = FontWeight.Thin,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .drawBehind {
                                val underlineHeight = 1.dp.toPx() // Line thickness
                                val y = size.height
                                drawRect(
                                    color = Color.White.copy(0.85f),
                                    topLeft = Offset(4.dp.toPx(), y - underlineHeight),
                                    size = Size(
                                        size.width - 8.dp.toPx(),
                                        underlineHeight - 2.7.dp.toPx()
                                    )
                                )
                            }
                            .clickWithRipple(Color.White) {
                                onDismiss()
                                changeSelectedItemState(1)
                                navController.navigate(Route.History(2)) {
                                    popUpTo(Route.BottomBarGraph) {
                                        saveState = true
                                    }
                                    // do not create a pile of historyScreen when repeated clicks
                                    launchSingleTop = true
                                    // restore the state if the tab was already opened before
                                    restoreState = true
                                }
                            }
                            .padding(bottom = 4.dp, top = 2.dp, start = 4.dp, end = 4.dp)
                    )
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