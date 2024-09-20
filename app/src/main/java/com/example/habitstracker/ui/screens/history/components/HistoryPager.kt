package com.example.habitstracker.ui.screens.history.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.habitstracker.ui.screens.history.tab_screens.AchievementsScreen
import com.example.habitstracker.ui.screens.history.tab_screens.AllHabitScreen
import com.example.habitstracker.ui.screens.history.tab_screens.HistoryCalendarScreen
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import com.example.habitstracker.ui.theme.screensBackgroundDark
import kotlinx.coroutines.launch

/**
 * Here I use TabRow and Horizontal Pager
 * */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryPager(modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf("Calendar", "All habits", "Achievements")
    val pagerState = rememberPagerState(pageCount = { tabs.size })

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
            containerColor = screensBackgroundDark,
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = { Text(text = tab, fontSize = 12.sp, fontFamily = PoppinsFontFamily) },
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

        HorizontalPager(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top,
            state = pagerState
        ) { page ->

            when (page) {
                0 -> HistoryCalendarScreen()

                1 -> AllHabitScreen()

                2 -> AchievementsScreen()
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