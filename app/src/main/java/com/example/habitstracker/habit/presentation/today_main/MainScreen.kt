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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.habitstracker.core.presentation.utils.shownHabitExample
import com.example.habitstracker.habit.domain.ShownHabit
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun TodayScreenRoot(viewModel: MainScreenViewModel) {
    val coroutineScope = rememberCoroutineScope()

    val habitListState by viewModel.habitsListState.collectAsStateWithLifecycle()
    val dateState by viewModel.selectedDate.collectAsStateWithLifecycle()
    val onSelectClick: (
        id: Int,
        isDone: Boolean,
        selectDate: String
    ) -> Unit = { id, isDone, selectDate ->
        viewModel.updateDateSelectState(id, isDone, selectDate)
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
        onSelectClick = onSelectClick,
        onDeleteClick = onDeleteClick,
        onDateChangeClick = onDateChangeClick
    )
}

@Composable
fun TodayScreen(
    modifier: Modifier = Modifier,
    habitListState: List<ShownHabit>,
    dateState: LocalDate,
    onSelectClick: (id: Int, isDone: Boolean, selectDate: String) -> Unit,
    onDeleteClick: (id: Int) -> Unit,
    onDateChangeClick   : (newDate: LocalDate) -> Unit
) {
    val navController = LocalNavController.current

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
                    CalendarRowList(
                        onDateChangeClick = { newDate ->
                            onDateChangeClick(newDate)
                        },
                        selectedDate = dateState
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

                        items(habitListState) { habit ->
                            HabitItem(
                                shownHabit = habit,
                                currentDate = dateState,
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
        shownHabitExample
    )

    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            TodayScreen(
                habitListState = mockList,
                onSelectClick = { _, _, _ -> },
                onDeleteClick = { _ -> },
                onDateChangeClick = {},
                dateState = LocalDate.now()
            )
        }
    }
}