package com.example.habitstracker.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.navigation.AppNavigation
import com.example.habitstracker.core.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // check if we have started through the link
        handleDeepLink(intent)

        setContent {
            val navController = rememberNavController()
            val settingsController = remember { SettingsSheetController() }

            CompositionLocalProvider(
                LocalNavController provides navController,
                LocalSettingsSheetController provides settingsController,
            ) {
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


                //IT IS IMPORTANT HERE:
                // 1. Save this ID somewhere (e.g. in ViewModel or SharedPrefs)
                // 2. Open the "Add a friend?" dialog or automatically send a request
                // friendViewModel.sendFriendRequest(friendId)
            }
        }
    }
}