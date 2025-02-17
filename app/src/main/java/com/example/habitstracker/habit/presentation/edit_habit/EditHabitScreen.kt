package com.example.habitstracker.habit.presentation.edit_habit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.habit.presentation.add_habit.components.AdvancedSettings
import com.example.habitstracker.habit.presentation.edit_habit.components.EditCreateButton
import com.example.habitstracker.habit.presentation.edit_habit.components.EditExecutionTimePicker
import com.example.habitstracker.habit.presentation.edit_habit.components.EditHabitNameTextField
import com.example.habitstracker.habit.presentation.edit_habit.components.EditIconAndColorPicker
import com.example.habitstracker.habit.presentation.edit_habit.components.scaffold.TopBarEditHabitScreen
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.habit.presentation.today_main.MainScreenViewModel
import com.example.habitstracker.core.presentation.utils.clickWithRipple
import com.example.habitstracker.core.presentation.utils.getColorFromHex
import com.example.habitstracker.core.presentation.utils.getCorrectSelectedDaysList
import com.example.habitstracker.core.presentation.utils.getIconName
import com.example.habitstracker.core.presentation.utils.iconByName
import com.example.habitstracker.core.presentation.utils.toHex
import com.example.habitstracker.habit.domain.ShownHabit
import kotlinx.coroutines.launch

@Composable
fun EditHabitRoot(
    paramId: Int,
    viewModel: MainScreenViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val allHabits = viewModel.habitsListState.collectAsState()
    val currentHabit = allHabits.value.find { it.id == paramId }

    val onUpdateHabitClick: (newHabit: HabitEntity) -> Unit = { newHabit ->
        coroutineScope.launch {
            viewModel.updateHabit(newHabit)
        }
    }

    if (currentHabit != null) {
        EditHabitScreen(
            onUpdateHabitClick = onUpdateHabitClick,
            habit = currentHabit,
            icon = iconByName(currentHabit.iconName), // pass the icon separately, otherwise compose will be not rendering
        )
    } else throw IllegalStateException("Habit was not found (EditHabitScreen)")

}

@Composable
fun EditHabitScreen(
    modifier: Modifier = Modifier,
    habit: ShownHabit,
    icon: ImageVector,
    onUpdateHabitClick: (habit: HabitEntity) -> Unit,
) {
    val navController = LocalNavController.current

    val dayStatesList = getCorrectSelectedDaysList(habit.days)

    Scaffold(
        topBar = { TopBarEditHabitScreen() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            var habitName by remember {
                mutableStateOf(habit.name)
            }

            var habitIcon by remember {
                mutableStateOf(icon)
            }

            var habitColor by remember {
                mutableStateOf(habit.colorHex.getColorFromHex())
            }

            var selectedDays = dayStatesList.let {
                val includeDaysList = it.filter { it.isSelect == true }.map { it.day }
                val excludeDaysList = it.filter { it.isSelect == false }.map { it.day }

                when {
                    includeDaysList.size == 7 -> "Everyday"
                    excludeDaysList.size == 1 -> "Everyday except ${excludeDaysList[0]}"
                    excludeDaysList.size == 2 -> "Everyday except ${excludeDaysList[0]} " + "and ${excludeDaysList[1]}"
                    includeDaysList.isNotEmpty() -> includeDaysList.joinToString(", ")

                    else -> "Error data (EditHabitScreen 'when' expression)"

                }
            }


            var executionTime by remember { mutableStateOf(habit.executionTime) }


            val onExecutionTimeButtonClick: (executionTimeText: String) -> Unit = { text ->
                executionTime = text
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                EditHabitNameTextField(name = habitName) { text ->
                    habitName = text
                }

                Spacer(modifier = modifier.height(16.dp))

                EditIconAndColorPicker(
                    icon = habitIcon,
                    color = habitColor,

                    onIconPick = { icon ->
                        habitIcon = iconByName(icon)
                    },
                    onColorPick = { color ->
                        habitColor = color
                    }
                )
                Spacer(modifier = modifier.height(12.dp))


                Text(
                    text = "REPEAT (Long-term habits)",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.50f),
                )

                Spacer(modifier = modifier.height(12.dp))


                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(color = screenContainerBackgroundDark)
                        .clickWithRipple(
                            color = Color.White
                        ) {

                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "param_selectedDays",
                                value = dayStatesList
                            )
                            navController.navigate(Route.EditRepeatPicker(id = habit.id))
                        },

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = modifier.padding(start = 16.dp),
                        text = stringResource(R.string.days_of_habits),
                        fontFamily = PoppinsFontFamily,
                        fontSize = 14.sp,
                        color = Color.White,
                    )

                    Row(
                        modifier = modifier.padding(end = 12.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = selectedDays,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.5f),
                        )

                        Spacer(modifier = modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White.copy(0.7f)
                        )
                    }
                }

                Spacer(modifier = modifier.height(12.dp))

                EditExecutionTimePicker(
                    currentPickedButton = executionTime,
                    onButtonClicked = onExecutionTimeButtonClick
                )
                Spacer(modifier = modifier.height(16.dp))

                AdvancedSettings()
            }


            // Create habit with new param and update
            val newHabit = HabitEntity(
                id = habit.id,
                name = habitName,
                iconName = getIconName(habitIcon),
                colorHex = habitColor.toHex(),
                days = habit.days,
                executionTime = executionTime,
                reminder = false
            )

            EditCreateButton(
                modifier = modifier.align(Alignment.BottomCenter),
                habit = newHabit,
                onUpdateHabit = onUpdateHabitClick
            )

        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    val mockNavController = rememberNavController()

    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            EditHabitScreen(
                habit = ShownHabit(),
                icon = Icons.Default.SentimentSatisfied,
                onUpdateHabitClick = {},
                /*previewList = listOf(
                    SelectedDay(true, "Mon"),
                    SelectedDay(true, "Tue"),
                    SelectedDay(true, "Wed"),
                    SelectedDay(false, "Thu"),
                    SelectedDay(false, "Fri"),
                    SelectedDay(false, "San"),
                    SelectedDay(false, "Sut")
                )*/
            )
        }
    }
}


