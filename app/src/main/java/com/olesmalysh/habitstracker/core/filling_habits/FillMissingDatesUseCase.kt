package com.olesmalysh.habitstracker.core.filling_habits

import com.olesmalysh.habitstracker.habit.domain.HabitRepository
import javax.inject.Inject

class FillMissingDatesUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
    //private val db: HabitDatabase, // Inject DB to run transaction
    //private val zoneIdProvider: ZoneIdProvider, // Optional, but better
) {
   /* suspend operator fun invoke(today: LocalDate = LocalDate.now(zoneIdProvider.zoneId())) {
        db.withTransaction {
            val habits = habitRepository.getAllHabitsOnce()

            for (habit in habits) {
                val habitId = habit.id

                val lastDateStr = habitRepository.getLastDateForHabit(habitId)
                val startDate = (lastDateStr?.let(LocalDate::parse) ?: today).plusDays(
                    if (lastDateStr == null) 0 else 1
                )

                if (startDate.isAfter(today)) continue

                val list = buildList {
                    var d = startDate
                    while (!d.isAfter(today)) {
                        add(
                            DateHabitEntity(
                                habitId = habitId,
                                currentDate = d.toString(),
                                completed = false
                            )
                        )
                        d = d.plusDays(1)
                    }
                }

                habitRepository.insertHabitDates(list) // calls DAO.insertAll(list)
            }
        }
    }*/
}