package com.example.habitstracker.me.data.local

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

    suspend fun fillMissingDatesForAllHabits() {
        val today = LocalDate.now()
        val habits = dao.getAllHabits().first()

        habits.forEach { habit ->

            val dates = dao.getAllDatesByHabitId(habit.id)

            if (dates.isEmpty()) {
                dao.insertHabitDate(
                    DateHabitEntity(
                        habitId = habit.id,
                        currentDate = today.toString(),
                        isCompleted = false
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
                            isCompleted = false
                        )
                    )
                }
                currentDate = currentDate.plusDays(1)
            }
        }
    }
}