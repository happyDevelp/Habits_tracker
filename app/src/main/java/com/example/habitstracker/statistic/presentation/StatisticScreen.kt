package com.example.habitstracker.statistic.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.MyPalette
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.core.presentation.utils.APP_VERSION
import com.example.habitstracker.core.presentation.utils.BirdAnimations
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.presentation.components.statistic_containers.CustomBlank
import com.example.habitstracker.history.presentation.components.statistic_containers.getFilledBlankList
import com.example.habitstracker.statistic.presentation.components.CustomStatisticContainer
import com.example.habitstracker.statistic.presentation.profile.components.ButtonItem
import com.example.habitstracker.statistic.presentation.profile.components.CustomContainer
import com.example.habitstracker.statistic.presentation.profile.components.SettingsButtonItem
import com.example.habitstracker.statistic.presentation.profile.components.scaffold.TopBarProfileScreen
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun StatisticScreenRoot(viewModel: StatisticViewModel) {
    val dateHabitList by viewModel.dateHabitList.collectAsStateWithLifecycle()

    val monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val week = (0..6).map { monday.plusDays(it.toLong()) }

    // calculate data for StatisticContainers section
    val completedPercentageList = week.map { date ->
        val habits = dateHabitList.filter { LocalDate.parse(it.currentDate) == date }
        if (habits.isEmpty()) 0f
        else habits.count { it.isCompleted }.toFloat() / habits.size.toFloat()
    }

    // calculate data for strength section
    val today = LocalDate.now()
    val monthAgo = today.minusMonths(1)

    val lastMonthHabits = dateHabitList.filter { dateHabit ->
        val date = LocalDate.parse(dateHabit.currentDate)
        date.isAfter(monthAgo.minusDays(1))
                && date.isBefore(today.plusDays(1))
    }

    val totalHabits = lastMonthHabits.size
    val completedHabits = lastMonthHabits.count { it.isCompleted }
    val strengthPercentage = (completedHabits.toFloat() / totalHabits.toFloat() * 100f).toInt()


    StatisticScreen(
        dateHabitList = dateHabitList,
        completedPercentageList = completedPercentageList,
        strengthPercentage = strengthPercentage
    )
}

@Composable
fun StatisticScreen(
    dateHabitList: List<DateHabitEntity>,
    completedPercentageList: List<Float>,
    strengthPercentage: Int
) {
    Scaffold(
        topBar = { TopBarProfileScreen() },
        containerColor = screenBackgroundDark
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState),
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

            // test animation
            BirdAnimations.HappyBird()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = containerBackgroundDark,
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 16.dp, bottom = 24.dp),
                    contentAlignment = Alignment.TopCenter
                ) {




                    Column(
                        modifier = Modifier
                            .wrapContentWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Consistency"/*"Monthly Consistency"*/,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = PoppinsFontFamily
                        )
                        Spacer(Modifier.height(32.dp))

                        val progress = strengthPercentage / 100.toFloat()
                        val strokeWidth = 14.dp
                        val diameter = 220.dp

                        Canvas(
                            modifier = Modifier
                                .width(diameter)
                                .height(diameter / 2)
                        ) {
                            val stroke = Stroke(
                                width = strokeWidth.toPx(),
                                cap = StrokeCap.Round
                            )
                            val topOffset = 0f/*-size.width / 2f*/
                            val stretchingDegree = 20

                            // grayBase
                            drawArc(
                                color = Color.LightGray.copy(alpha = 0.3f),
                                startAngle = 180f,
                                sweepAngle = 180f,
                                useCenter = false,
                                topLeft = Offset(-stretchingDegree / 2f, topOffset),
                                size = Size(size.width + stretchingDegree, size.width),
                                style = stroke
                            )

                            // The yellow filled part
                            drawArc(
                                color = Color(0xFFFFC107),
                                startAngle = 180f,
                                sweepAngle = 180f * progress,
                                useCenter = false,
                                topLeft = Offset(-stretchingDegree / 2f, topOffset),
                                size = Size(size.width + stretchingDegree, size.width),
                                style = stroke
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 4.dp),
                        text = "$strengthPercentage%",
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    text = "Perform your habits daily to increase your consistency percentage.",
                    fontSize = 12.sp,
                    color = Color.White.copy(0.88f),
                    fontFamily = PoppinsFontFamily,
                    textAlign = TextAlign.Center
                )
            }

            CustomStatisticContainer(
                height = 300.dp,
                percentageList = completedPercentageList
            )

            CustomContainer(
                modifier = Modifier
                    .padding(top = 20.dp, start = 12.dp, end = 12.dp)
                    .padding(paddingValues)
                    .height(180.dp),
            ) {
                val topButtonList = getTopButtonsList()
                DrawTopButtons(topButtonList)
            }

            CustomContainer(
                modifier = Modifier
                    .padding(top = 30.dp, start = 12.dp, end = 12.dp)
                    .height(180.dp),
            ) {
                val bottomButtonList = getBottomButtonsList()
                DrawTopButtons(bottomButtonList)
            }

            MyText(
                modifier = Modifier.padding(top = 12.dp),
                text = "Version $APP_VERSION",
                textSize = 15.sp,
                color = Color.White.copy(0.6f)
            )

        }
    }
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
fun DrawStatisticContainers(dateHabitList: List<DateHabitEntity>) {
    val context = LocalContext.current

    /** current streak and the best streak*/
    val currentStreak = getCurrentStreak(dateHabitList)
    val bestStreak = getBestStreak(dateHabitList)

    /** total completed habits  */
    val totalCompletedHabits = dateHabitList.count { it.isCompleted }


    /** total completed habits this week */
    val currentDate = LocalDate.now()
    val lastMonday =
        remember(currentDate) {
            currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        }
    val thisWeekSelectedHabits = dateHabitList
        .filter { it.currentDate >= lastMonday.toString() }
        .count { it.isCompleted }


    /** Percentage of completed habits */
    val totalHabits = dateHabitList.size
    val percentage = totalCompletedHabits / totalHabits.toFloat() * 100

    /** Perfect days */
    val perfectDaysCounter by remember {
        mutableIntStateOf(
            dateHabitList
                .groupBy { it.currentDate }
                .count { (_, habits) -> habits.all { it.isCompleted } }
        )
    }

    val thisWeekPerfectedDays by remember {
        mutableIntStateOf(
            dateHabitList
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
                    gradientColor = blankItem.gradientColor,
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
@Preview(showSystemUi = false)
private fun ProfileScreenPreview() {
    AppTheme(darkTheme = true) {
        StatisticScreen(
            dateHabitList = emptyList(),
            completedPercentageList = emptyList(),
            strengthPercentage = 25
        )
    }
}