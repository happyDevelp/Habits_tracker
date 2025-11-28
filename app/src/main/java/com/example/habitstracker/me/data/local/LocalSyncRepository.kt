package com.example.habitstracker.me.data.local

import android.util.Log
import com.example.habitstracker.habit.data.db.HabitDao
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class LocalSyncRepository @Inject constructor(private val dao: HabitDao) {
    suspend fun getAllHabitsOnce(): List<HabitEntity> {
        return dao.getAllHabits().first()
    }

    suspend fun getAllDateHabitsOnce(): List<DateHabitEntity> {
        return dao.getAllDateHabits().first()
    }

    suspend fun insertHabits(list: List<HabitEntity>) {
        list.forEach { dao.insertHabit(it) }
    }

    suspend fun insertDates(list: List<DateHabitEntity>) {
        list.forEach { dao.insertHabitDate(it) }
    }

/**
     * Complete replacement of local data with data from the cloud.
     * 1) clean the tables
     * 2) insert habits
     * 3) insert ONLY valid dates (so as not to fall on FOREIGN KEY)
     * 4) fill in the missed dates
     */
    suspend fun replaceAllFromCloud(
        habits: List<HabitEntity>,
        dates: List<DateHabitEntity>
    ) {
        // 1. filter dates that refer to non-existent habits
        val habitIds = habits.map { it.id }.toSet()
        val validDates = dates.filter { it.habitId in habitIds }

        dao.replaceAllFromCloud(habits, validDates)

        fillMissingDatesForAllHabits()
    }

    suspend fun fillMissingDatesForAllHabits() {
        val today = LocalDate.now()
        val habits = dao.getAllHabits().first()

        Log.d("SYNC_DEBUG", "fillMissingDatesForAllHabits")

        habits.forEach { habit ->

            val dates = dao.getAllDatesByHabitId(habit.id)

            if (dates.isEmpty()) {
                dao.insertHabitDate(
                    DateHabitEntity(
                        habitId = habit.id,
                        currentDate = today.toString(),
                        completed = false
                    )
                )
                return@forEach
            }

            val lastDate = LocalDate.parse(dates.last().currentDate)
            var currentDate = lastDate.plusDays(1)

            while (!currentDate.isAfter(today)) {
                val exists = dao.dateExistsForHabit(habit.id, currentDate.toString())
                if (exists == false) {

                    dao.insertHabitDate(
                        DateHabitEntity(
                            habitId = habit.id,
                            currentDate = currentDate.toString(),
                            completed = false
                        )
                    )
                }
                currentDate = currentDate.plusDays(1)
            }
        }
    }
}