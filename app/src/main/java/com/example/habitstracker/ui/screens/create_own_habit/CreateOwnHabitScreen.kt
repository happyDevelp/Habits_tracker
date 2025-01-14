package com.example.habitstracker.ui.screens.create_own_habit

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
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.data.db.HabitEntity
import com.example.habitstracker.data.db.viewmodel.HabitViewModel
import com.example.habitstracker.navigation.Route
import com.example.habitstracker.ui.screens.add_habit.components.AdvancedSettings
import com.example.habitstracker.ui.screens.add_habit.components.CreateButton
import com.example.habitstracker.ui.screens.add_habit.components.ExecutionTimePicker
import com.example.habitstracker.ui.screens.add_habit.components.IconAndColorPicker
import com.example.habitstracker.ui.screens.create_own_habit.components.HabitNameTextField
import com.example.habitstracker.ui.screens.create_own_habit.scaffold.TopBarCreateOwnHabitScreen
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import com.example.habitstracker.ui.theme.orangeColor
import com.example.habitstracker.ui.theme.screenContainerBackgroundDark
import com.example.habitstracker.utils.clickWithRipple
import com.example.habitstracker.utils.getIconName
import com.example.habitstracker.utils.iconByName
import com.example.habitstracker.utils.toHex

@Composable
fun CreateOwnHabitContent(
    param: String,
    modifier: Modifier = Modifier,
    onAddHabit: (
        name: String, iconName: String, isDone: Boolean, colorHex: Color,
        days: String, executionTime: String, reminder: Boolean,
    ) -> Unit,
) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = { TopBarCreateOwnHabitScreen() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            var habitName by remember { mutableStateOf("") }

            var habitIconName by remember {
                mutableStateOf(getIconName(Icons.Filled.SentimentVerySatisfied))
            }

            var habitColor by remember { mutableStateOf(orangeColor) }
            val selectedDays by remember { mutableStateOf(param) }
            var executionTime by remember { mutableStateOf("Anytime") }


            val onExecutionTimeButtonClickListener: (executionTimeText: String) -> Unit = { text ->
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
                HabitNameTextField(name = habitName) { text ->
                    habitName = text
                }
                Spacer(modifier = modifier.height(16.dp))

                IconAndColorPicker(
                    icon = iconByName(habitIconName),
                    color = habitColor,

                    onIconPick = { icon ->
                        habitIconName = icon
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
                        ) { navController.navigate(Route.RepeatPicker) },
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
                            text = param,
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

                ExecutionTimePicker(onButtonClicked = onExecutionTimeButtonClickListener)
                Spacer(modifier = modifier.height(16.dp))

                AdvancedSettings()
            }

            CreateButton(
                modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                navController,
                name = habitName,
                iconName = habitIconName,
                color = habitColor,
                executionTime = executionTime,
                selectedDays = selectedDays,
                onAddHabit = onAddHabit
            )
        }
    }
}

@Composable
fun CreateOwnHabitScreen(param: String = "no value") {
    val viewModel = hiltViewModel<HabitViewModel>()
    val _onAddHabit = {
            name: String, iconName: String, isDone: Boolean, colorHex: Color,
            days: String, executionTime: String, reminder: Boolean,
        ->
        viewModel.addHabit(
            HabitEntity(
                name = name,
                iconName = iconName,
                isDone = false,
                colorHex = colorHex.toHex(),
                days = days,
                executionTime = executionTime,
                reminder = false
            )
        )
    }

    CreateOwnHabitContent(param = param, onAddHabit = _onAddHabit)
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            CreateOwnHabitContent(param = "Fake param",
                onAddHabit = { _, _, _, _, _, _, _ -> }
            )
        }
    }
}