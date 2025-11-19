package com.example.habitstracker.me.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.syncDataStore by preferencesDataStore("sync_prefs")

class SyncPreferences(private val context: Context) {
    companion object {
        val LAST_SYNC_KEY = stringPreferencesKey("last_sync")
    }

    val lastSync: Flow<String?> = context.syncDataStore.data.map { prefs ->
        prefs[LAST_SYNC_KEY]
    }


    suspend fun saveLastSync(value: String) {
        context.syncDataStore.edit { prefs ->
            prefs[LAST_SYNC_KEY] = value
        }
    }
}