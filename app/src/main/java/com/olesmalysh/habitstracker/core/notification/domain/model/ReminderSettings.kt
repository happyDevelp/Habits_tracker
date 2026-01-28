package com.olesmalysh.habitstracker.core.notification.domain.model

data class ReminderSettings(
    val enabled: Boolean = true,
    val hour: Int = 20,
    val minute: Int = 0
) {
    val timeText: String get() = "%02d:%02d".format(hour, minute)
}