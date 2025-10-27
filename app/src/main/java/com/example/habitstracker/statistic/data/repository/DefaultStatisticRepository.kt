package com.example.habitstracker.statistic.data.repository

import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.statistic.data.db.StatisticDao
import com.example.habitstracker.statistic.domain.StatisticRepository
import kotlinx.coroutines.flow.Flow

class DefaultStatisticRepository(private val dao: StatisticDao): StatisticRepository {
    override fun getDateHabitList(): Flow<List<DateHabitEntity>> {
            return dao.getDateHabitList()
    }
}