package com.olesmalysh.habitstracker.core.notification.domain

import com.olesmalysh.habitstracker.core.notification.domain.model.ReminderSettings
import kotlinx.coroutines.flow.Flow

interface ReminderSettingsRepository {
    val settings: Flow<ReminderSettings>

    suspend fun setEnabled(enabled: Boolean)
    suspend fun setTime(hour: Int, minute: Int)

    suspend fun isBootstrapped(): Boolean
    suspend fun setBootstrapped(value: Boolean)
}