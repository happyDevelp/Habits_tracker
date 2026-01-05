package com.olesmalysh.habitstracker.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.core.app.NotificationCompat
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.olesmalysh.habitstracker.R
import com.olesmalysh.habitstracker.app.navigation.AppNavigation
import com.olesmalysh.habitstracker.core.data.worker.ReminderWorker
import com.olesmalysh.habitstracker.core.presentation.theme.AppTheme
import com.olesmalysh.habitstracker.core.presentation.utils.RequestNotificationPermission
import com.olesmalysh.habitstracker.core.presentation.utils.calculateInitialDelay
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // enableEdgeToEdge()
        setContent {

            // check if we have started through the link
            handleDeepLink(intent)

// Calculate the time until the nearest reminder (your calculateInitialDelay function)
            val initialDelay = calculateInitialDelay()

            val firstWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .addTag("daily_reminder")
                .build()

//We use REPLACE so that if the user returns to the application, the timer is updated
            WorkManager.getInstance(applicationContext).enqueueUniqueWork(
                "DailyHabitReminder",
                ExistingWorkPolicy.REPLACE, // Або KEEP, якщо не хочете збивати таймер при кожному вході
                firstWorkRequest
            )

            val navController = rememberNavController()
            val settingsController = remember { SettingsSheetController() }

            CompositionLocalProvider(
                LocalNavController provides navController,
                LocalSettingsSheetController provides settingsController,
            ) {
                AppTheme(darkTheme = true) {

                   /* val dailyWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
                        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS) //Delay until the right time (eg 9:00 am)
                        .addTag("daily_reminder")
                        .build()

                    WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                        "DailyHabitReminder",
                        ExistingPeriodicWorkPolicy.UPDATE,
                        dailyWorkRequest
                    )*/

                    RequestNotificationPermission()
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

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // 1. Показуємо сповіщення
        try {
            val text = applicationContext.getString(R.string.don_t_forget_to_fulfill_your_goals_today)
            showNotification(text)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 2. ПЛАНУЄМО НАСТУПНИЙ ЗАПУСК (на завтра)
        scheduleNextWork(applicationContext)

        return Result.success()
    }

    private fun scheduleNextWork(context: Context) {
        // Завжди плануємо через 24 години
        val nextDelay = TimeUnit.HOURS.toMillis(24)

        val nextWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(nextDelay, TimeUnit.MILLISECONDS)
            .addTag("daily_reminder")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "DailyHabitReminder", // Використовуємо те саме ім'я
            ExistingWorkPolicy.REPLACE, // Перезаписуємо старий запит новим
            nextWorkRequest
        )
    }

    private fun showNotification(text: String) {
        // ... ваш код відображення сповіщення (він вірний) ...
        val channelId = "habit_reminder_channel"
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Habit Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Habits Tracker")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}