package com.olesmalysh.habitstracker.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.olesmalysh.habitstracker.R
import com.olesmalysh.habitstracker.app.MainActivity
import com.olesmalysh.habitstracker.core.presentation.UiText
import com.olesmalysh.habitstracker.habit.domain.HabitRepository
import com.olesmalysh.habitstracker.profile.data.local.AppPreferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.ZonedDateTime

@HiltWorker
class DailyReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val habitRep: HabitRepository,
    private val appPreferences: AppPreferences,
    private val dailyReminderScheduler: DailyReminderScheduler
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            Log.d("DailyReminderWorker", "doWork START at=${ZonedDateTime.now()}")

            val enabled = appPreferences.reminderEnabled.first()
            Log.d("DailyReminderWorker", "enabled=$enabled")

            if (!enabled) {
                Log.d("DailyReminderWorker", "disabled -> success")
                return Result.success()
            }
            // 2) Count incomplete habits for today

            val today = LocalDate.now().toString()
            val remaining = habitRep.countIncompleteForDate(today)
            Log.d("DailyReminderWorker", "today=$today remaining=$remaining")

            // 3) Build notification text
            val text = randomText(remaining).asString(applicationContext)
            Log.d("DailyReminderWorker", "notification text=$text")

            showNotification(text)

            Log.d("DailyReminderWorker", "notification SHOWN at=${ZonedDateTime.now()}")

            val hour = appPreferences.reminderHour.first()
            val minute = appPreferences.reminderMinute.first()

            // 4) Schedule next run for tomorrow at selected time
            dailyReminderScheduler.schedule(hour, minute)

            Log.d("DailyReminderWorker", "next schedule requested at=${ZonedDateTime.now()}")

            Result.success()
        } catch (e: Exception) {
            Log.e("DailyReminderWorker", "doWork FAILED", e)
            Result.retry()
        }
    }


    private fun showNotification(text: String) {
        val channelId = "daily_reminder_channel"
        val notificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Daily reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Create an intent that opens the app
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            // Optional: if you want to route inside the app
            putExtra("open_screen", "today")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        // Create proper back stack for navigation
        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setContentTitle("OnTrack: Habit Tracker")
            .setContentText(text)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(2001, notification)
    }

    private fun randomText(remaining: Int): UiText {
        val remainingList = listOf(
            UiText.StringResources(R.string.something_wrong_you_still_have_habits_left_for_today, remaining),
            UiText.StringResources(R.string.everything_okay_habits_are_still_waiting_for_you, remaining),
            UiText.StringResources(R.string.don_t_forget_habits_are_still_unfinished_today, remaining),
            UiText.StringResources(R.string.stay_focused_habits_are_still_unfinished, remaining)
        )


        val allDoneList = listOf(
            UiText.StringResources(R.string.all_habits_for_today_are_completed_great_job),
            UiText.StringResources(R.string.you_did_it_everything_is_done_for_today),
            UiText.StringResources(R.string.perfect_day_all_habits_completed)
        )

        return if (remaining > 0) {
            remainingList.random()
        } else {
            allDoneList.random()
        }
    }
}

