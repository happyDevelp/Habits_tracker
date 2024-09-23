package com.example.habitstracker.ui.screens.create_own_habit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.ui.screens.add_habit.components.AdvancedSettings
import com.example.habitstracker.ui.screens.add_habit.components.CreateButton
import com.example.habitstracker.ui.screens.add_habit.components.ExecutionTimePicker
import com.example.habitstracker.ui.screens.add_habit.components.IconAndColorPicker
import com.example.habitstracker.ui.screens.add_habit.components.RepeatPicker
import com.example.habitstracker.ui.screens.create_own_habit.components.HabitNameTextField
import com.example.habitstracker.ui.screens.create_own_habit.scaffold.TopBarCreateOwnHabitScreen
import com.example.habitstracker.ui.theme.AppTheme

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) { CreateOwnHabitScreen() }
    }
}

@Composable
fun CreateOwnHabitScreen(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = { TopBarCreateOwnHabitScreen() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) { paddingValues ->
        Box(modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {

                HabitNameTextField()
                Spacer(modifier = modifier.height(16.dp))

                IconAndColorPicker()
                Spacer(modifier = modifier.height(12.dp))

                RepeatPicker(navController = navController)
                Spacer(modifier = modifier.height(12.dp))

                ExecutionTimePicker()
                Spacer(modifier = modifier.height(16.dp))

                AdvancedSettings()
            }

            CreateButton(modifier.align(Alignment.BottomCenter), navController)
        }
    }
}