package com.example.habitstracker.profile.presentation.friends.components

import android.content.Context
import android.content.Intent

fun shareFriendLink(context: Context, myProfileId: String) {
    // 1. Forming a unique link
    val deepLink = "habitstracker://invite?userId=$myProfileId"

    // 2. Add the message text.
    // Since the application is not in the store, we can also add a link to the APK (for example, to Google Drive)
    val message = "Hello! Add me as a friend in Habit Tracker.\n" +
            "Here you can copy my friendID: $myProfileId"/* +
            "(If the application is not installed, download it here: https://bit.ly/ link_to_your_apk)"*/

    // 3. Creating an Intent
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }

    // 4. Launch the "Share Sheet"
    val shareIntent = Intent.createChooser(sendIntent, "Share link via:")
    context.startActivity(shareIntent)
}