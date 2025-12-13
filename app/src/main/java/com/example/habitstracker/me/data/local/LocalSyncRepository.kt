package com.example.habitstracker.me.data.local

import android.util.Log
import com.example.habitstracker.habit.data.db.HabitDao
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.history.data.db.HistoryDAO
import com.example.habitstracker.history.domain.AchievementEntity
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class LocalSyncRepository @Inject constructor(
    private val habitDao: HabitDao,
    private val historyDao: HistoryDAO,
) {
    suspend fun getAllHabitsOnce(): List<HabitEntity> {
        return habitDao.getAllHabits().first()
    }

    suspend fun getAllDateHabitsOnce(): List<DateHabitEntity> {
        return habitDao.getAllDateHabits().first()
    }

    suspend fun getAllAchievementsOnce(): List<AchievementEntity> {
        return historyDao.getAllAchievementsOnce()
    }

    suspend fun insertHabits(list: List<HabitEntity>) {
        list.forEach { habitDao.insertHabit(it) }
    }

    suspend fun insertDates(list: List<DateHabitEntity>) {
        list.forEach { habitDao.insertHabitDate(it) }
    }

    /**
     * Replace all local achievements with the provided list from the cloud.
     */
    suspend fun replaceAchievementsFromCloud(achievements: List<AchievementEntity>) {
        historyDao.clearAchievements()
        historyDao.insertAchievements(achievements)
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

        habitDao.replaceAllFromCloud(habits, validDates)

        fillMissingDatesForAllHabits()
    }

    suspend fun fillMissingDatesForAllHabits() {
        val today = LocalDate.now()
        val habits = habitDao.getAllHabits().first()

        Log.d("SYNC_DEBUG", "fillMissingDatesForAllHabits")

        habits.forEach { habit ->

            val dates = habitDao.getAllDatesByHabitId(habit.id)

            if (dates.isEmpty()) {
                habitDao.insertHabitDate(
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
                val exists = habitDao.dateExistsForHabit(habit.id, currentDate.toString())
                if (exists == false) {

                    habitDao.insertHabitDate(
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