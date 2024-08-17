package com.example.habitstracker.ui.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.ui.newHabit.CreateNewHabit
import com.example.habitstracker.ui.theme.AppTheme

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
                            composable("AppScreen") {
                                MainScreen()
                            }
                            composable("CreateNewHabit") {
                                CreateNewHabit()
                            }
                        }
                    )
                }
            }
        }
    }
}

