package com.example.habitstracker.profile.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.MyPalette
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.core.presentation.utils.APP_VERSION
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.presentation.tab_screens.DrawStatisticContainers
import com.example.habitstracker.profile.presentation.profile.components.ButtonItem
import com.example.habitstracker.profile.presentation.profile.components.CustomContainer
import com.example.habitstracker.profile.presentation.profile.components.CustomStatisticContainer
import com.example.habitstracker.profile.presentation.profile.components.SettingsButtonItem
import com.example.habitstracker.profile.presentation.profile.components.scaffold.TopBarProfileScreen
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

@Composable
@Preview(showSystemUi = false)
private fun ProfileScreenPreview() {
    AppTheme(darkTheme = true) { ProfileScreen(streakList = emptyList()) }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, streakList: List<DateHabitEntity>) {
    Scaffold(
        topBar = { TopBarProfileScreen() },
        containerColor = screenBackgroundDark
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            val monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            val week = (0..6).map { monday.plusDays(it.toLong()) }

            val completedPercentageList = week.map { date ->
                val habits = streakList.filter { LocalDate.parse(it.currentDate) == date }
                if (habits.isEmpty()) 0f
                else habits.count { it.isCompleted }.toFloat() / habits.size.toFloat()
            }

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
                modifier = modifier.padding(start = 12.5.dp, top = 6.dp),
                text = formattedCurrentDate,
                textSize = 14.sp,
                color = Color.White.copy(0.7f)
            )

            // percentage list of completed habits
            //val completedHabitsList = listOf(0.66f, 0.33f, 1f, 0f, 0f, 0f, 0f)

            DrawStatisticContainers(streakList = streakList)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(horizontal = 12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = containerBackgroundDark,

                    )
            ) {

                Column(
                    Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(18.dp))
                    Text(
                        text = "Strength",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = PoppinsFontFamily
                    )

                    Spacer(Modifier.height(24.dp))

                    val strokeWidth = 16.dp
                    val startAngle = 180f  // початок зліва
                    val sweepAngle = 180f  // півколо

                    Canvas(
                        modifier = Modifier
                            .size(180.dp)
                            .padding(8.dp)
                    ) {
                        val stroke = Stroke(
                            width = strokeWidth.toPx(), cap = StrokeCap.Round
                        )

                        drawArc(
                            color = Color.LightGray.copy(0.3f),
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = stroke
                        )

                        // заповнена частина
                        drawArc(
                            color = Color(0xFFFFC107),
                            startAngle = startAngle,
                            sweepAngle = sweepAngle * 0.2f,
                            useCenter = false,
                            style = stroke
                        )

                    }
                }
            }



            CustomStatisticContainer(
                height = 300.dp,
                percentageList = completedPercentageList
            )

            CustomContainer(
                modifier = modifier
                    .padding(top = 20.dp, start = 12.dp, end = 12.dp)
                    .padding(paddingValues)
                    .height(180.dp),
            ) {
                val topButtonList = getTopButtonsList()
                DrawTopButtons(topButtonList)
            }

            CustomContainer(
                modifier = modifier
                    .padding(top = 30.dp, start = 12.dp, end = 12.dp)
                    .height(180.dp),
            ) {
                val bottomButtonList = getBottomButtonsList()
                DrawTopButtons(bottomButtonList)
            }

            MyText(
                modifier = modifier.padding(top = 12.dp),
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