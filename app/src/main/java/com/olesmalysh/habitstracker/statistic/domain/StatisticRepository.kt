package com.olesmalysh.habitstracker.statistic.domain

import com.olesmalysh.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {
        fun getDateHabitList(): Flow<List<DateHabitEntity>>

}