package com.example.habitstracker.ui.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.ui.addHabit.AddHabitScreen
import com.example.habitstracker.ui.сreateOwnHabit.CreateOwnHabitScreen
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.сreateOwnHabit.RepeatPicker
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController provided")
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            CompositionLocalProvider(value = LocalNavController provides navController) {

                AppTheme(darkTheme = true) {

                    NavHost(
                        navController = navController,
                        startDestination = "AppScreen",
                        builder = {
                            composable(getString(R.string.app_screen_navigation)) {
                                MainScreen()
                            }
                            composable(getString(R.string.add_habit_screen_navigation)) {
                                AddHabitScreen()
                            }
                            composable(getString(R.string.create_own_habit_navigation)) {
                                CreateOwnHabitScreen()
                            }
                            composable(getString(R.string.repeat_picker_navigation)) {
                                RepeatPicker()
                            }
                        }

                    )

                }

            }

        }
    }
}

