package com.example.habitstracker.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.navigation.AppNavigation
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //FirebaseApp.initializeApp(this)
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