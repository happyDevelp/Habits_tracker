package com.example.habitstracker.history.presentation.tab_screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screensBackgroundDark

@Preview(showSystemUi = true)
@Composable
fun AchievementsScreenPreview(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(screensBackgroundDark)
    )
    { AchievementsScreen() }
}

@Composable
fun AchievementsScreen() {
    val sections = listOf(
        // first section
        AchievementSection(
            title = stringResource(R.string.achiev_habits_finished),
            achievedText = "4/6 achieved",
            iconRes = R.drawable.dart_board,
            targets = listOf("1", "10", "25", "100", "500", "1000")
        ),

        // second section
        AchievementSection(
            title = stringResource(R.string.achiev_perfect_days),
            achievedText = "2/6 achieved",
            iconRes = R.drawable.calendar_hexagon,
            targets = listOf("3", "10", "25", "50", "100", "250")
        ),

        //third section
        AchievementSection(
            title = stringResource(R.string.achiev_beast_streak),
            achievedText = "5/6 achieved",
            iconRes = R.drawable.streak_achiev,
            targets = listOf("3", "5", "10", "20", "50", "100")
        ),
    )

    LazyColumn {
        items(sections.size) { index ->
            HabitsFinishedBox(
                section = sections[index],
            )
        }
    }
}


@Composable
fun HabitsFinishedBox(
    modifier: Modifier = Modifier,
    section: AchievementSection,
) {
    Card(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp)
        ,
        colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark)
    ) {
        Column(
            modifier = modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = section.title,
                fontSize = 20.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = section.achievedText,
                fontSize = 11.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.White.copy(0.7f),
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .height(265.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                userScrollEnabled = false
            ) {
                items(section.targets.size) { index ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = section.iconRes),
                                contentDescription = "achievements picture",
                                modifier = Modifier.size(85.dp),
                                tint = Color.Unspecified
                            )

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .offset(
                                        y =
                                            when (section.title) {
                                                stringResource(R.string.achiev_habits_finished) -> (-7).dp
                                                stringResource(R.string.achiev_perfect_days) -> (-27).dp
                                                stringResource(R.string.achiev_beast_streak) -> (2).dp

                                                else -> 0.dp
                                            }
                                    )
                                    .height(24.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = section.targets[index],
                                    fontSize = 15.sp,
                                    fontFamily = PoppinsFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = blueColor,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text =
                                when (section.title) {
                                    stringResource(R.string.achiev_habits_finished) -> {
                                        if (index == 0) "Finish Your Habit First Time"
                                        else "Finish Habit For The ${section.targets[index]} Times"
                                    }

                                    stringResource(R.string.achiev_perfect_days) ->
                                        "${section.targets[index]} Perfect Days"

                                    stringResource(R.string.achiev_beast_streak) ->
                                        "${section.targets[index]} Days Streak"

                                    else -> "0.dp"
                                },
                            fontSize = 11.sp,
                            fontFamily = PoppinsFontFamily,
                            lineHeight = 12.sp,
                            color = Color.White.copy(0.9f),
                            modifier = Modifier.width(80.dp + 8.dp),
                            textAlign = TextAlign.Center,
                            softWrap = true,
                            overflow = TextOverflow.Clip,
                        )
                    }
                }
            }
        }
    }
}

data class AchievementSection(
    val title: String,
    val achievedText: String,
    @DrawableRes val iconRes: Int,
    val targets: List<String>, // ["1","10","25",...]
    val description: (String) -> String = { t ->
        if (t == "1") "Finish Your Habit First Time"
        else "Finish Habit For The $t Times"
    }
)