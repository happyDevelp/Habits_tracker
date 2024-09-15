package com.example.habitstracker.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.navigation.AppNavigation
import com.example.habitstracker.ui.theme.AppTheme

val LocalNavController = compositionLocalOf<NavHostController> {
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
                    AppNavigation()
                }
            }
        }
    }
}