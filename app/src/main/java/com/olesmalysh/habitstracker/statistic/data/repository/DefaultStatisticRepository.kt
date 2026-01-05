package com.olesmalysh.habitstracker.statistic.data.repository

import com.olesmalysh.habitstracker.habit.domain.DateHabitEntity
import com.olesmalysh.habitstracker.statistic.data.db.StatisticDao
import com.olesmalysh.habitstracker.statistic.domain.StatisticRepository
import kotlinx.coroutines.flow.Flow

class DefaultStatisticRepository(private val dao: StatisticDao): StatisticRepository {
    override fun getDateHabitList(): Flow<List<DateHabitEntity>> {
            return dao.getDateHabitList()
    }
}