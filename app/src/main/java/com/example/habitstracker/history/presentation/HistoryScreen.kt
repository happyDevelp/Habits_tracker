package com.example.habitstracker.history.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.history.presentation.components.scaffold.TopBarHistoryScreen
import com.example.habitstracker.history.presentation.tab_screens.AchievementsScreen
import com.example.habitstracker.history.presentation.tab_screens.AllHabitScreen
import com.example.habitstracker.history.presentation.tab_screens.HistoryCalendarScreen
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun HistoryScreenRoot(
    historyViewModel: HistoryViewModel,
    startTab: Int,
    changeSelectedItemState: (index: Int) -> Unit
) {
    val streakList by historyViewModel.dateHabitList.collectAsStateWithLifecycle()
    val allAchievements by historyViewModel.allAchievements.collectAsStateWithLifecycle()

    HistoryScreen(
        changeSelectedItemState = changeSelectedItemState,
        streakList = streakList,
        allAchievements = allAchievements,
        startTab = startTab
    )
}

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    streakList: List<DateHabitEntity>,
    allAchievements: List<AchievementEntity>,
    startTab: Int,
    changeSelectedItemState: (index: Int) -> Unit,
) {
    Scaffold(
        topBar = { TopBarHistoryScreen() },
        containerColor = screenBackgroundDark
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val coroutineScope = rememberCoroutineScope()
            val tabs = listOf("Calendar", "All habits", "Achievements")
            val pagerState = rememberPagerState(
                initialPage = startTab,
                pageCount = { tabs.size }
            )

            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                val indicator = @Composable { tabPosition: List<TabPosition> ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(pagerState, tabPosition),
                        color = MaterialTheme.colorScheme.primaryContainer,
                    )
                }

                TabRow(
                    modifier = modifier,
                    selectedTabIndex = pagerState.currentPage,
                    indicator = indicator, // Need to fix lag with last rowIndex when sliding (or delete indicator)
                    containerColor = screenBackgroundDark,
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            text = {
                                Text(
                                    text = tab,
                                    fontSize = 12.sp,
                                    fontFamily = PoppinsFontFamily
                                )
                            },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.White.copy(alpha = 0.65f),

                            modifier = modifier.size(50.dp),
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        )
                    }
                }

                val mapHabitsToDate = remember(streakList) {
                    streakList.groupBy { LocalDate.parse(it.currentDate) }
                }
                HorizontalPager(
                    modifier = modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Top,
                    state = pagerState
                ) { page ->
                    when (page) {
                        0 -> HistoryCalendarScreen(
                            changeSelectedItemState = changeSelectedItemState,
                            streakList = streakList,
                            mapDateToHabits = mapHabitsToDate
                        )

                        1 -> AllHabitScreen()

                        2 -> AchievementsScreen(
                            mapHabitsToDate = mapHabitsToDate,
                            allAchievements = allAchievements,
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.tabIndicatorOffset(pagerState: PagerState, tabPositions: List<TabPosition>): Modifier {
    return if (pagerState.pageCount == 0) {
        Modifier
    } else {
        // Calculate offset based on the current page offset and position
        val currentTabPosition = tabPositions[pagerState.currentPage]
        val nextTabPosition = tabPositions.getOrNull(pagerState.currentPage + 1)
        val fraction = pagerState.currentPageOffsetFraction

        // Interpolating between the current and next tab
        if (nextTabPosition != null) {
            Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.BottomStart)
                .offset(x = currentTabPosition.left + fraction * (nextTabPosition.left - currentTabPosition.left))
                .width(currentTabPosition.width)
        } else {
            // For the last tab, keep the indicator at the last position
            Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.BottomStart)
                .offset(x = currentTabPosition.left)
                .width(currentTabPosition.width)
        }
    }
}

@Composable
@Preview(showSystemUi = false)
private fun HistoryScreenPreview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            HistoryScreen(
                changeSelectedItemState = {},
                allAchievements = emptyList(),
                streakList = emptyList(),
                startTab = 0
            )
        }
    }
}