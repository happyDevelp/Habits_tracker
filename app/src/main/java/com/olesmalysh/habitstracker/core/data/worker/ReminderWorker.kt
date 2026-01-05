package com.olesmalysh.habitstracker.core.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.olesmalysh.habitstracker.R
import com.olesmalysh.habitstracker.app.MainActivity

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val message = "Do not forget to fulfill your habits for today"
        showNotification(message)
        return Result.success()
    }

    private fun showNotification(text: String) {
        val channelId = "habit_reminder_channel"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 1. Створюємо Intent, який вказує на MainActivity// 1. Create an Intent that points to MainActivity
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            // These flags say: if the app is open, don't make a copy of it,
            // and open an existing or create a new stack.
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // 2.Wrap Intent in PendingIntent
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            // FLAG_IMMUTABLE required for Android 12+ (API 31+)
            PendingIntent.FLAG_IMMUTABLE
        )

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
            .setAutoCancel(true) // The notification will disappear after clicking
            .setContentIntent(pendingIntent) // ADD INTENT
            .build()

        notificationManager.notify(1, notification)
    }

}