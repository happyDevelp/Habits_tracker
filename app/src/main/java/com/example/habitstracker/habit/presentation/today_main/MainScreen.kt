package com.example.habitstracker.habit.presentation.today_main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.CustomRippleTheme
import com.example.habitstracker.habit.presentation.today_main.components.HabitItem
import com.example.habitstracker.habit.presentation.today_main.components.calendar.CalendarRowList
import com.example.habitstracker.habit.presentation.today_main.components.TopBarMainScreen
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screensBackgroundDark
import com.example.habitstracker.core.presentation.utils.TestTags
import com.example.habitstracker.core.presentation.utils.generateDateSequence
import com.example.habitstracker.core.presentation.utils.habitEntityExample
import com.example.habitstracker.habit.domain.HabitDateEntity
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun TodayScreenRoot(viewModel: MainScreenViewModel) {
    val coroutineScope = rememberCoroutineScope()

    val habitListState by viewModel.habitsListState.collectAsStateWithLifecycle()
    val onSelectClick: (id: Int, isDone: Boolean) -> Unit = { id, isDone ->
        viewModel.updateHabitAndDateSelectState(id, isDone)
    }

    val onDeleteClick: (habit: HabitEntity) -> Unit = { habit ->
        coroutineScope.launch {
            viewModel.deleteHabit(habit)
        }
    }

    val onDateClick: (date: String) -> Unit = { date ->
        coroutineScope.launch {
            viewModel.setHabitsForDate(date)
        }
    }

    TodayScreen(
        habitListState = habitListState,
        onSelectClick = onSelectClick,
        onDeleteClick = onDeleteClick,
        onDateClick = onDateClick
    )
}

@Composable
fun TodayScreen(
    modifier: Modifier = Modifier,
    habitListState: List<HabitEntity>,
    onSelectClick: (id: Int, isDone: Boolean) -> Unit,
    onDeleteClick: (habit: HabitEntity) -> Unit,
    onDateClick: (date: String) -> Unit
) {
    val navController = LocalNavController.current
    var currentDate by remember {
        mutableStateOf(LocalDate.now().minusDays(5))
    }
    val onCurrentDayChange: (newDate: LocalDate) -> Unit = { newDate ->
        currentDate = newDate
    }

    Scaffold(topBar = { TopBarMainScreen(modifier, navController) }) { paddingValues ->
        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            colors = CardDefaults.cardColors(
                containerColor = screensBackgroundDark,
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

                Column {
                    val dateSet = generateDateSequence(
                        currentDate, 500
                    )
                    CalendarRowList(
                        dateSet.toMutableList(),
                        onCurrentDayChange = onCurrentDayChange,
                        onDateClick = onDateClick
                    )
                }

                CompositionLocalProvider(
                    value = LocalRippleTheme provides CustomRippleTheme(color = Color.Black)
                ) {

                    LazyColumn(
                        modifier = modifier
                            .padding(top = 95.dp)
                            .fillMaxHeight(1f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        items(habitListState.size) { habitId ->
                            HabitItem(
                                habit = habitListState[habitId],
                                onSelectClick = onSelectClick,
                                onDeleteClick = onDeleteClick,
                            )
                            Spacer(modifier = modifier.height(20.dp))
                        }

                        item {
                            Spacer(modifier = modifier.height(6.dp))

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

@Composable
@Preview(showSystemUi = false)
private fun Preview() {
    val mockNavController = rememberNavController()
    val mockList = listOf(
        habitEntityExample
    )

    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            TodayScreen(
                habitListState = mockList,
                onSelectClick = {_,_-> },
                onDeleteClick = {},
                onDateClick = {}
            )
        }
    }
}