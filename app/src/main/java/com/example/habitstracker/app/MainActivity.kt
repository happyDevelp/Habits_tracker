package com.example.habitstracker.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.navigation.AppNavigation
import com.example.habitstracker.core.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // check if we have started through the link
        handleDeepLink(intent)

        setContent {
            val navController = rememberNavController()

            CompositionLocalProvider(value = LocalNavController provides navController) {
                AppTheme(darkTheme = true) {
                    AppNavigation()
                }
            }
        }
    }

    // If the application was already open and we clicked on the link (SingleTask mode)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        val data: Uri? = intent?.data
        // Check if the scheme is ours and if the host is "invite"
        if (data != null && data.scheme == "habitstracker" && data.host == "invite") {
            val friendId = data.getQueryParameter("userId")

            if (friendId != null) {
                Log.d("FriendDeepLink", "Friend ID Found: $friendId")




                // ТУТ ВАЖЛИВО:
                // 1. Збережи цей ID десь (наприклад, у ViewModel або SharedPrefs)
                // 2. Відкрий діалог "Додати друга?" або автоматично надішли запит
                // friendViewModel.sendFriendRequest(friendId)
            }
        }
    }
}