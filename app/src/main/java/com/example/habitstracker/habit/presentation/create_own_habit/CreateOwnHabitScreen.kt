package com.example.habitstracker.habit.presentation.create_own_habit

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.orangeColor
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.utils.clickWithRipple
import com.example.habitstracker.core.presentation.utils.getColorFromHex
import com.example.habitstracker.core.presentation.utils.getIconName
import com.example.habitstracker.core.presentation.utils.iconByName
import com.example.habitstracker.core.presentation.utils.toHex
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.presentation.add_habit.components.AdvancedSettings
import com.example.habitstracker.habit.presentation.add_habit.components.CreateButton
import com.example.habitstracker.habit.presentation.add_habit.components.ExecutionTimePicker
import com.example.habitstracker.habit.presentation.add_habit.components.IconAndColorPicker
import com.example.habitstracker.habit.presentation.create_own_habit.components.HabitNameTextField
import com.example.habitstracker.habit.presentation.today_main.MainScreenViewModel
import java.time.LocalDate

@Composable
fun CreateOwnHabitRoot(
    param: String = "no value",
    viewModel: MainScreenViewModel,
    name: String?,
    icon: String?,
    iconColor: String?
) {
    val onAddHabitClick: (habit: HabitEntity) -> Unit = { habit ->
        viewModel.insertHabit(habit) { habitId ->
            val currentDate = LocalDate.now().toString()
            viewModel.insertHabitDate(
                DateHabitEntity(
                    habitId = habitId.toInt(),
                    currentDate = currentDate,
                )
            )
        }
    }
    CreateOwnHabitScreen(
        param = param,
        onAddHabitClick = onAddHabitClick,
        name = name,
        icon = icon,
        iconColor = iconColor
    )
}

@Composable
fun CreateOwnHabitScreen(
    param: String,
    modifier: Modifier = Modifier,
    onAddHabitClick: (habit: HabitEntity) -> Unit,
    name: String?,
    icon: String?,
    iconColor: String?
) {
    val navController = LocalNavController.current
    Scaffold(
        topBar = { CustomTopBar(navController) },
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            var habitName by remember {
                mutableStateOf(
                    if (name != null) name
                    else ""
                )
            }
            var habitIconName by remember {
                mutableStateOf(
                    if (icon != null) icon // androidx.compose.ui.graphics.vector.ImageVector@8fd356ba
                    else getIconName(Icons.Filled.SentimentVerySatisfied) // SentimentVerySatisfied
                )
            }
            var habitColor by remember {
                mutableStateOf(
                    if (iconColor != null)
                        iconColor.getColorFromHex()
                    else orangeColor
                )
            }
            val selectedDays by remember { mutableStateOf(param) }
            var executionTime by remember { mutableStateOf("Anytime") }

            val onExecutionTimeButtonClickListener: (executionTimeText: String) -> Unit = { text ->
                executionTime = text
            }
            Column(modifier = modifier.fillMaxSize()) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(3.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    HabitNameTextField(name = habitName) { text ->
                        habitName = text
                    }
                    Spacer(modifier = modifier.height(16.dp))
                }

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
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
            }


            val habit = HabitEntity(
                name = habitName,
                iconName = habitIconName,
                colorHex = habitColor.toHex(),
                days = selectedDays,
                executionTime = executionTime,
            )
            CreateButton(
                modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                navController,
                habit = habit,
                onAddHabitClick = onAddHabitClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTopBar(navController: NavController) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel",
                    modifier = Modifier.size(26.dp),
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            CreateOwnHabitScreen(
                param = "Fake param",
                onAddHabitClick = { },
                name = null,
                icon = null,
                iconColor = null
            )
        }
    }
}