package com.example.habitstracker.app

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val STATISTIC_DATASTORE = "statistic_datastore"
val Context.preferenceDataStore: DataStore<Preferences> by preferencesDataStore(name = STATISTIC_DATASTORE)

class DataStoreManager(val context: Context) {
    companion object {
        private val BEST_STREAK = intPreferencesKey("BEST_STREAK")
        private val COMPLETED_HABITS = intPreferencesKey("COMPLETED_HABITS")
        private val COMPLETED_HABITS_THIS_WEEK = intPreferencesKey("COMPLETED_HABITS_THIS_WEEK")
        private val PERCENTAGE_OF_COMPLETED_HABITS = intPreferencesKey("PERCENTAGE_OF_COMPLETED_HABITS")
        private val TOTAL_COMPLETE = intPreferencesKey("TOTAL_COMPLETE")
        private val PERFECT_DAYS = intPreferencesKey("PERFECT_DAYS")
        private val PERFECT_DAYS_ON_THIS_WEEK = intPreferencesKey("PERFECT_DAYS_ON_THIS_WEEK")
    }

    suspend fun saveBestStreak(value: Int) {
        context.preferenceDataStore.edit {  preferences ->
            preferences[BEST_STREAK] = value
        }
    }

    fun getBestStreak() = context.preferenceDataStore.data.map { preferences ->
        preferences[BEST_STREAK] ?: 999
    }

    suspend fun clearDataStore() = context.preferenceDataStore.edit { preferences ->
        preferences.clear()
    }
}