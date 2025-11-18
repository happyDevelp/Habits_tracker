package com.example.habitstracker.me.data.local

import android.util.Log
import com.example.habitstracker.habit.data.db.HabitDao
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class LocalSyncRepository @Inject constructor(private val dao: HabitDao) {
    suspend fun clearAllLocalData() {
        dao.clearDateTable()
        dao.clearHabitTable()
    }

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

    suspend fun fillMissingDatesForAllHabits() {
        val today = LocalDate.now()
        val test_tag = "SYNC_TEST"

        val habits = dao.getAllHabits().first()
        Log.d(test_tag, "1111")

        habits.forEach { habit ->
            Log.d(test_tag, "2222")

            val dates = dao.getAllDatesByHabitId(habit.id)
            Log.d(test_tag, "3333")

            if (dates.isEmpty()) {
                dao.insertHabitDate(DateHabitEntity(
                    habitId = habit.id,
                    currentDate = today.toString(),
                    isCompleted = false
                ))
                Log.d(test_tag, "4444")

                return@forEach
            }

            val lastDate = LocalDate.parse(dates.last().currentDate)
            var currentDate = lastDate.plusDays(1)
            Log.d("SYNC", "5555")

            while (!currentDate.isAfter(today)) {
                val exists = dao.dateExistsForHabit(habit.id, currentDate.toString())
                if (exists == false) {
                    Log.d("SYNC", "6666")

                    dao.insertHabitDate(DateHabitEntity(
                        habitId = habit.id,
                        currentDate = currentDate.toString(),
                        isCompleted = false
                    ))
                    Log.d(test_tag, "7777")

                }
                currentDate = currentDate.plusDays(1)
            }
        }
    }
}