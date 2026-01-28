package com.olesmalysh.habitstracker.core.notification.data

import com.olesmalysh.habitstracker.core.notification.domain.ReminderSettingsRepository
import com.olesmalysh.habitstracker.core.notification.domain.model.ReminderSettings
import com.olesmalysh.habitstracker.profile.data.local.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class DefaultReminderSettingsRepository(
    private val prefs: AppPreferences,
) : ReminderSettingsRepository {

    override val settings: Flow<ReminderSettings> =
        combine(
            prefs.reminderEnabled,
            prefs.reminderHour,
            prefs.reminderMinute
        ) { enabled, hour, minute ->
            // Map DataStore flows into a single immutable model
            ReminderSettings(
                enabled = enabled,
                hour = hour,
                minute = minute
            )
        }

    override suspend fun setEnabled(enabled: Boolean) {
        prefs.setReminderEnabled(enabled)
    }

    override suspend fun setTime(hour: Int, minute: Int) {
        prefs.setReminderTime(hour, minute)
    }

    override suspend fun isBootstrapped(): Boolean {
        return prefs.reminderBootstrapped.first()
    }

    override suspend fun setBootstrapped(value: Boolean) {
        prefs.setReminderBootstrapped(value)
    }
}