package com.example.habitstracker.statistic.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.MyPalette
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.core.presentation.utils.APP_VERSION
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.statistic.presentation.components.DrawConsistencyContainer
import com.example.habitstracker.statistic.presentation.components.DrawStatisticContainers
import com.example.habitstracker.statistic.presentation.components.WeekDiagramContainer
import com.example.habitstracker.statistic.presentation.profile.components.ButtonItem
import com.example.habitstracker.statistic.presentation.profile.components.SettingsButtonItem
import com.example.habitstracker.statistic.presentation.profile.components.scaffold.TopBarProfileScreen
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import kotlin.math.roundToInt

@Composable
fun StatisticScreenRoot(viewModel: StatisticViewModel) {
    val dateHabitList by viewModel.dateHabitList.collectAsStateWithLifecycle()

    val consistency = rolling30DayConsistency(dateHabitList)

    // data for AVG diagram
    val weeklyMap = remember(dateHabitList) {
        groupHabitsByWeek(dateHabitList)
            .toSortedMap(compareByDescending { it.first }) // sort that the new weeks are the first
    }


    StatisticScreen(
        dateHabitList = dateHabitList,
        consistency = consistency,
        weeklyMap = weeklyMap
    )
}

@Composable
fun StatisticScreen(
    dateHabitList: List<DateHabitEntity>,
    consistency: Int,
    weeklyMap: Map<Pair<LocalDate, LocalDate>, List<DateHabitEntity>>
) {
    Scaffold(
        topBar = { TopBarProfileScreen() },
        containerColor = screenBackgroundDark
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            MyText(
                modifier = Modifier.padding(start = 16.dp),
                text = "STATISTICS",
                textSize = 18.sp
            )

            DrawStatisticContainers(dateHabitList = dateHabitList)

            DrawConsistencyContainer(consistency)
            Spacer(Modifier.height(18.dp))

            WeekDiagramContainer(_weeklyMap = weeklyMap)

            /*            SettingsContainer(
                            modifier = Modifier
                                .padding(top = 20.dp, start = 12.dp, end = 12.dp)
                                .padding(paddingValues)
                                .height(180.dp),
                        ) {
                            val topButtonList = getTopButtonsList()
                            DrawTopButtons(topButtonList)
                        }

                        SettingsContainer(
                            modifier = Modifier
                                .padding(top = 30.dp, start = 12.dp, end = 12.dp)
                                .height(180.dp),
                        ) {
                            val bottomButtonList = getBottomButtonsList()
                            DrawTopButtons(bottomButtonList)
                        }*/

            MyText(
                modifier = Modifier.padding(top = 12.dp),
                text = "Version $APP_VERSION",
                textSize = 15.sp,
                color = Color.White.copy(0.6f)
            )
        }
    }
}

private fun rolling30DayConsistency(allHabits: List<DateHabitEntity>): Int {
    if (allHabits.isEmpty()) return 0

    val today = LocalDate.now()
    val startDate = today.minusDays(29)

    // group by date
    val habitsByDate = allHabits.groupBy { LocalDate.parse(it.currentDate) }

    var totalRatio = 0.0
    var current = startDate

    // go through 30 days
    while (!current.isAfter(today)) {
        val habitsForDay = habitsByDate[current].orEmpty()
        val totalForDay = habitsForDay.size
        val completedForDay = habitsForDay.count { it.isCompleted }

        val ratio = if (totalForDay > 0) {
            completedForDay.toDouble() / totalForDay
        } else 0.0 // if there were no habits on this day, count as 0


        totalRatio += ratio
        current = current.plusDays(1)
    }

    // average performance for 30 days as a percentage
    val consistency = (totalRatio / 30.0) * 100.0
    return consistency.roundToInt()
}

private fun groupHabitsByWeek(dateHabitList: List<DateHabitEntity>)
        : Map<Pair<LocalDate, LocalDate>, List<DateHabitEntity>> =
    dateHabitList.groupBy { habit ->
        val date = LocalDate.parse(habit.currentDate)
        val monday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val sunday = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        monday to sunday
    }

@Composable
private fun DrawTopButtons(buttonList: List<ButtonItem>) {
    buttonList.forEach { button ->
        SettingsButtonItem(
            icon = button.icon,
            iconBackground = button.iconBackground,
            text = button.text
        )
    }
}

@Composable
fun getTopButtonsList() =
    listOf(
        ButtonItem(
            icon = Icons.Default.Notifications,
            iconBackground = MyPalette.blueColor,
            text = stringResource(id = R.string.notification)
        ),
        ButtonItem(
            icon = Icons.Default.Settings,
            iconBackground = MyPalette.orangeColor,
            text = stringResource(R.string.general_settings)
        ),
        ButtonItem(
            icon = Icons.Default.Language,
            iconBackground = MyPalette.redColor,
            text = stringResource(R.string.language_options)
        ),
    )

@Composable
fun getBottomButtonsList() =
    listOf(
        ButtonItem(
            icon = Icons.Default.Share,
            iconBackground = MyPalette.blueColor,
            text = stringResource(R.string.share_with_friends)
        ),
        ButtonItem(
            icon = Icons.Default.Star,
            iconBackground = Color(0xFF14AD8E),
            text = stringResource(R.string.rate_us)
        ),
        ButtonItem(
            icon = Icons.Default.Feedback,
            iconBackground = Color(0xFF4788D6),
            text = stringResource(R.string.feedback)
        ),
    )

@Composable
@Preview(showSystemUi = false)
private fun ProfileScreenPreview() {
    AppTheme(darkTheme = true) {
        StatisticScreen(
            dateHabitList = emptyList(),
            consistency = 25,
            weeklyMap = mapOf(
                Pair(
                    Pair(LocalDate.now().minusDays(7), LocalDate.now()),
                    listOf(
                        DateHabitEntity(
                            id = 1,
                            habitId = 1,
                            currentDate = "2025-10-30",
                            isCompleted = true
                        ),
                        DateHabitEntity(
                            id = 2,
                            habitId = 2,
                            currentDate = "2025-10-30",
                            isCompleted = false
                        )
                    )
                )
            )
        )
    }
}