package com.example.habitstracker.me.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
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

    // Method for clearing data when logging out of account
    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}