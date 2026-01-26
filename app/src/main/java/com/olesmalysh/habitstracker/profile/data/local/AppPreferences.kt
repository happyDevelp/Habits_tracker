package com.olesmalysh.habitstracker.profile.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("sync_prefs")

class AppPreferences(private val context: Context) {
    companion object {
        val LAST_SYNC_KEY = stringPreferencesKey("last_sync")
        val PROFILE_ID_KEY = stringPreferencesKey("profile_code")
        val DEEP_LINK_FRIEND_ID = stringPreferencesKey("deep_link_friend_id")

        // Reminder
        val REMINDER_ENABLED_KEY = booleanPreferencesKey("reminder_enabled")
        val REMINDER_HOUR_KEY = intPreferencesKey("reminder_hour")
        val REMINDER_MINUTE_KEY = intPreferencesKey("reminder_minute")
    }
    val lastSync: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[LAST_SYNC_KEY]
    }

    val profileCode: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PROFILE_ID_KEY]
    }

    val deepLinkFriendId: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[DEEP_LINK_FRIEND_ID]
    }

    // Reminder
    val reminderEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[REMINDER_ENABLED_KEY] ?: true
    }

    val reminderHour: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[REMINDER_HOUR_KEY] ?: 20
    }

    val reminderMinute: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[REMINDER_MINUTE_KEY] ?: 0
    }

    suspend fun saveLastSync(value: String) {
        context.dataStore.edit { prefs ->
            prefs[LAST_SYNC_KEY] = value
        }
    }

    suspend fun saveProfileCode(code: String) {
        context.dataStore.edit { prefs ->
            prefs[PROFILE_ID_KEY] = code
        }
    }

    suspend fun saveDeepLinkFriendId(id: String) {
        context.dataStore.edit { prefs ->
            prefs[DEEP_LINK_FRIEND_ID] = id
        }
    }

    // Reminder
    suspend fun setReminderEnabled(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[REMINDER_ENABLED_KEY] = value
        }
    }

    suspend fun setReminderTime(hour: Int, minute: Int) {
        context.dataStore.edit {
            it[REMINDER_HOUR_KEY] = hour
            it[REMINDER_MINUTE_KEY] = minute
        }
    }

    // Method for clearing data when logging out of account
    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}