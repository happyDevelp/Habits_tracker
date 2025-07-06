package com.example.habitstracker.history.data.db

import androidx.room.Dao
import androidx.room.Query
import com.example.habitstracker.habit.domain.DateHabitEntity

@Dao
sealed interface HistoryDAO {
    @Query("select * from date_table where isCompleted = 1")
    fun getAllDatesForStreak(): List<DateHabitEntity>
}