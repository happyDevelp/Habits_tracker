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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.navigation.RoutesMainScreen
import com.example.habitstracker.ui.screens.add_habit.components.AdvancedSettings
import com.example.habitstracker.ui.screens.add_habit.components.CreateButton
import com.example.habitstracker.ui.screens.add_habit.components.ExecutionTimePicker
import com.example.habitstracker.ui.screens.add_habit.components.IconAndColorPicker
import com.example.habitstracker.ui.screens.add_habit.components.RepeatPickerContainer
import com.example.habitstracker.ui.screens.create_own_habit.components.HabitNameTextField
import com.example.habitstracker.ui.screens.create_own_habit.scaffold.TopBarCreateOwnHabitScreen
import com.example.habitstracker.ui.screens.today_main.getIconName
import com.example.habitstracker.ui.screens.today_main.iconByName
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import com.example.habitstracker.ui.theme.orangeColor
import com.example.habitstracker.ui.theme.screenContainerBackgroundDark
import com.example.habitstracker.utils.clickWithRipple

@Composable
fun CreateOwnHabitScreen(param: String = "no value", modifier: Modifier = Modifier) {
    val navController = LocalNavController.current

    val viewModel: CreateOwnHabitViewModel =
        viewModel(modelClass = CreateOwnHabitViewModel::class)

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
            var daysOfHabit by remember { mutableStateOf(viewModel._selectedDays.value) }

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
                        ) { navController.navigate(RoutesMainScreen.RepeatPicker.route) },
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

                ExecutionTimePicker()
                Spacer(modifier = modifier.height(16.dp))

                AdvancedSettings()
            }

            CreateButton(
                modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp), navController,
                name = habitName,
                iconName = habitIconName,
                color = habitColor
            )
        }
    }
}

@Composable @Preview(showSystemUi = true)
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) { CreateOwnHabitScreen() }
    }
}