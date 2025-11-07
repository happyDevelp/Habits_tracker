package com.example.habitstracker.statistic.data.db

import androidx.room.Dao
import androidx.room.Query
import com.example.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface StatisticDao {
    @Query("select * from date_table order by currentDate desc")
    fun getDateHabitList(): Flow<List<DateHabitEntity>>
}