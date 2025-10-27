package com.example.habitstracker.statistic.domain

import com.example.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {
        fun getDateHabitList(): Flow<List<DateHabitEntity>>

}