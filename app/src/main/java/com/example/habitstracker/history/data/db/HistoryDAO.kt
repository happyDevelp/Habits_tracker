package com.example.habitstracker.history.data.db

import androidx.room.Dao
import androidx.room.Query
import com.example.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface HistoryDAO {
    @Query("select * from date_table order by currentDate desc")
    fun getAllDatesForStreak(): Flow<List<DateHabitEntity>>
}