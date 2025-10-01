package com.example.habitstracker.history.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
import com.example.habitstracker.core.presentation.theme.MyPalette
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.history.presentation.tab_screens.AchievementSection

@Composable
fun AchievementBox(
    modifier: Modifier = Modifier,
    section: AchievementSection,
    allAchievements: List<AchievementEntity>
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }

    if (showDialog && allAchievements.isNotEmpty()) {
        AchievementDialog(
            onDismiss = { showDialog = false },
            // Safe Verification to avoid IndexOutOfBoundException
            date = allAchievements.getOrNull(selectedIndex)?.unlockedAt ?: "",
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
            modifier = Modifier
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

            // New, not "lazy" grid
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                section.targets.chunked(3).forEach { rowItems ->
                    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {

                        rowItems.forEach { targetValue ->
                            val globalIndex = section.targets.indexOf(targetValue)

                            Box(modifier = Modifier.weight(1f)) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable{
                                            showDialog = true
                                            selectedIndex = globalIndex
                                        },
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        val target = targetValue.toInt()
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
                                                text = targetValue,
                                                fontSize = 15.sp,
                                                fontFamily = PoppinsFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isAchieved) MyPalette.blueColor else Color.Black.copy(0.5f),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = section.description.invoke(targetValue, globalIndex),
                                        fontSize = 11.sp,
                                        fontFamily = PoppinsFontFamily,
                                        lineHeight = 12.sp,
                                        color = Color.White.copy(0.9f),
                                        modifier = Modifier.widthIn(max = 90.dp),
                                        textAlign = TextAlign.Center,
                                        softWrap = true,
                                        overflow = TextOverflow.Clip,
                                    )
                                }
                            }
                        }
                        // add empty items if the row is incomplete
                        val emptySpaces = 3 - rowItems.size
                        if (emptySpaces > 0) {
                            repeat(emptySpaces) {
                                Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HabitBoxPreview() {
    AchievementBox(
        modifier = Modifier,
        section = AchievementSection(
            title = "a",
            iconRes = R.drawable.dart_board,
            targets = listOf("10", "10", "10", "10", "10", "10", "10", "10", "10", "10"),
            progress = 10,
            description = { a, _ -> a },
            isNotified = emptyList(),
            unlockedAt = emptyList()
        ),
        allAchievements = emptyList()
    )
}