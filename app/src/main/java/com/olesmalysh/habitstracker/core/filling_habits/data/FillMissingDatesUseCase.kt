package com.olesmalysh.habitstracker.core.filling_habits.data

import com.olesmalysh.habitstracker.habit.domain.DateHabitEntity
import com.olesmalysh.habitstracker.habit.domain.HabitRepository
import java.time.LocalDate
import javax.inject.Inject

class FillMissingDatesUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
) {
    suspend operator fun invoke(today: LocalDate = LocalDate.now()) {
        val habits = habitRepository.getAllHabitsOnce()

        for (habit in habits) {
            val habitId = habit.id

            val lastDateEntity = habitRepository.getLastDateForHabit(habitId)

            val startDate = if (lastDateEntity == null)
                today
            else {
                LocalDate.parse(lastDateEntity.currentDate).plusDays(1)
            }

            var current = startDate
            while (!current.isAfter(today)) {

                val exists = habitRepository.dateExistsForHabit(habitId, current.toString())

                if(!exists) {
                    habitRepository.insertHabitDate(
                        DateHabitEntity(
                            habitId = habitId, currentDate = current.toString(),
                            completed = false
                        )
                    )
                }

                current = current.plusDays(1)
            }
        }
    }
}