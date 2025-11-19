package com.example.habitstracker.habit.presentation.create_own_habit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.HabitColor
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.core.presentation.utils.clickWithRipple
import com.example.habitstracker.core.presentation.utils.getColorFromHex
import com.example.habitstracker.core.presentation.utils.getIconName
import com.example.habitstracker.core.presentation.utils.iconByName
import com.example.habitstracker.core.presentation.utils.toHex
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.presentation.add_habit.components.AdvancedSettings
import com.example.habitstracker.habit.presentation.add_habit.components.ExecutionTimePicker
import com.example.habitstracker.habit.presentation.add_habit.components.IconAndColorPicker
import com.example.habitstracker.habit.presentation.create_own_habit.components.HabitNameTextField
import com.example.habitstracker.habit.presentation.today_main.MainScreenViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun CreateOwnHabitRoot(
    viewModel: MainScreenViewModel = hiltViewModel<MainScreenViewModel>(),
    isEditMode: Boolean,
    id: Int?,
    name: String,
    icon: String,
    iconColor: String
) {
    val onActionButtonClick: (habit: HabitEntity) -> Unit = { habit ->
        if (isEditMode) {
            viewModel.updateHabit(habit)
        } else {
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
    }
    CreateOwnHabitScreen(
        isEditMode = isEditMode,
        id = id,
        name = name,
        icon = icon,
        iconColor = iconColor,
        onActionButtonClick = onActionButtonClick,
    )
}

@Composable
fun CreateOwnHabitScreen(
    modifier: Modifier = Modifier,
    isEditMode: Boolean,
    id: Int?,
    name: String,
    icon: String,
    iconColor: String,
    onActionButtonClick: (habit: HabitEntity) -> Unit,
) {

    val navController = LocalNavController.current
    Scaffold(
        topBar = {
            CustomTopBar(navController, title = if (isEditMode) "Edit" else "Create")
        },
        containerColor = screenBackgroundDark,
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            var habitName by remember {
                mutableStateOf(name)
            }
            var habitIconName by remember {
                mutableStateOf(icon)
            }
            var habitColor by remember {
                mutableStateOf(iconColor.getColorFromHex())
            }
            var selectedDays by remember { mutableStateOf<List<String>>(emptyList()) }
            var executionTime by remember { mutableStateOf("Anytime") }

            val onExecutionTimeButtonClickListener: (executionTimeText: String) -> Unit = { text ->
                executionTime = text
            }
            Column(modifier = modifier.fillMaxSize()) {
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        HabitNameTextField(name = habitName, color = habitColor) { text ->
                            habitName = text
                        }
                        Spacer(modifier = modifier.height(16.dp))
                    }
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

                    Spacer(modifier = modifier.height(28.dp))

                    Text(
                        text = "Repetitions",
                        fontFamily = PoppinsFontFamily,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.50f),
                    )
                    Spacer(modifier = modifier.height(12.dp))

                    DaysOfWeekPicker(
                        color = habitColor,
                        onDaysChanged = { days ->
                            selectedDays = days
                        }
                    )

                    Spacer(modifier = modifier.height(28.dp))

                    ExecutionTimePicker(onButtonClicked = onExecutionTimeButtonClickListener)
                    Spacer(modifier = modifier.height(16.dp))

                    AdvancedSettings()
                }
            }
            val coroutineScope = rememberCoroutineScope()

            val habit = HabitEntity(
                id = id ?: 0,
                name = habitName,
                iconName = habitIconName,
                colorHex = habitColor.toHex(),
                days = selectedDays.joinToString(","),
                executionTime = executionTime,
            )

            Button(
                modifier = modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(0.9f)
                    .height(60.dp)
                    .border(
                        1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(corner = CornerSize(50.dp))
                    ),
                enabled = habit.name.length >= 4,
                onClick = {
                    coroutineScope.launch {
                        onActionButtonClick(habit)
                        navController.popBackStack(Route.Today(), false)
                    }
                },

                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = Color.White,
                    disabledContainerColor = Color.White.copy(0.05f),
                    disabledContentColor = Color.White.copy(0.6f)
                ),

                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 16.dp
                )
            ) {
                Text(
                    text = if (isEditMode) stringResource(R.string.edit_habit)
                    else stringResource(R.string.add_habit),
                    fontSize = 20.sp,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTopBar(navController: NavController, title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontFamily = PoppinsFontFamily,
                fontSize = 20.sp
            )
        },
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
            containerColor = screenBackgroundDark
        )
    )
}


@Composable
private fun DaysOfWeekPicker(
    modifier: Modifier = Modifier,
    color: Color,
    onDaysChanged: (List<String>) -> Unit
) {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    // by default all selected
    var selectedDays by remember { mutableStateOf(days.toSet()) }
    onDaysChanged(selectedDays.toList())


    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEach { day ->
                DayItem(
                    day = day,
                    isSelected = selectedDays.contains(day),
                    color = color,
                    onClick = {
                        // do not allow to remove the last day
                        if (selectedDays.contains(day) && selectedDays.size == 1) {
                            return@DayItem // do nothing
                        }
                        selectedDays = if (selectedDays.contains(day)) {
                            selectedDays - day
                        } else {
                            selectedDays + day
                        }
                        onDaysChanged(selectedDays.toList())
                    }
                )
            }
        }
    }
}

@Composable
private fun DayItem(
    day: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else Color(0xFF404B53)
            )
            .clickWithRipple {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day,
            color = if (isSelected) Color.White else Color.White.copy(0.6f),
            fontFamily = PoppinsFontFamily,
            fontSize = 14.sp
        )
    }
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            CreateOwnHabitScreen(
                onActionButtonClick = { },
                name = "",
                icon = getIconName(Icons.Filled.SentimentVerySatisfied),
                iconColor = HabitColor.Orange.light.toHex(),
                isEditMode = false,
                id = null
            )
        }
    }
}