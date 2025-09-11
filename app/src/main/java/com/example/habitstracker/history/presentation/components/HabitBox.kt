package com.example.habitstracker.history.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.example.habitstracker.history.presentation.tab_screens.AchievementSection
import kotlin.text.toInt

@Composable
fun HabitBox(
    modifier: Modifier = Modifier,
    section: AchievementSection,
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }

    if (showDialog) {
        AchievementDialog(
            onDismiss = {
                showDialog = false
            },
            date = "12.12.2023",
            section = section,
            index = selectedIndex,
        )
    }

    val unlockedAchievements = section.targets.count { section.progress >= it.toInt() }
    val totalAchievements = section.targets.size

    Card(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp),
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
                text = "$unlockedAchievements/$totalAchievements unlocked",
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
                                showDialog = true
                                selectedIndex = index
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            val target = section.targets[index].toInt()
                            val isAchieved = section.progress >= target

                            Icon(
                                painter = painterResource(id = section.iconRes),
                                contentDescription = "achievements picture",
                                modifier = Modifier
                                    .size(
                                        if (section.title == "Best Streak") 80.dp else 85.dp
                                    )
                                    .graphicsLayer {
                                        if (!isAchieved) {
                                            alpha = 0.5f
                                        }
                                    },
                                tint = Color.Unspecified
                            )

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .offset(
                                        y =
                                            when (section.title) {
                                                stringResource(R.string.achiev_habits_finished) -> (-5).dp
                                                stringResource(R.string.achiev_perfect_days) -> (-28).dp
                                                stringResource(R.string.achiev_best_streak) -> (4).dp

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
                                    color = if (isAchieved) blueColor else Color.Black.copy(0.5f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = section.description.invoke(section.targets[index], index),
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

@Preview(showBackground = true)
@Composable
fun HabitBoxPreview() {
    HabitBox(
        modifier = Modifier,
        section = AchievementSection(
            title = "a",
            iconRes = R.drawable.dart_board,
            targets = listOf("10", "10", "10", "10", "10", "10", "10", "10", "10", "10"),
            progress = 10,
            description = { a, _ -> a }
        )
    )
}