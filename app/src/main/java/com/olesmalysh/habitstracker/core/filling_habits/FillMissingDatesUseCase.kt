package com.olesmalysh.habitstracker.core.filling_habits

import androidx.room.withTransaction
import com.olesmalysh.habitstracker.habit.data.db.HabitDatabase
import com.olesmalysh.habitstracker.habit.domain.DateHabitEntity
import com.olesmalysh.habitstracker.habit.domain.HabitRepository
import java.time.LocalDate
import javax.inject.Inject

class FillMissingDatesUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
    private val db: HabitDatabase
) {
    suspend operator fun invoke(today: LocalDate = LocalDate.now()) {
        db.withTransaction {
            val habits = habitRepository.getAllHabitsOnce()

            for (habit in habits) {
                val habitId = habit.id
                val habitUid = habit.uid

                // Get last existing date for this habit (fast)
                val lastDateStr = habitRepository.getLastDateStringForHabit(habitId)
                val startDate = if (lastDateStr == null) {
                    today
                } else {
                    LocalDate.parse(lastDateStr).plusDays(1)
                }

                if (startDate.isAfter(today)) continue

                // Build a list of missing dates
                val toInsert = buildList {
                    var d = startDate
                    while (!d.isAfter(today)) {
                        add(
                            DateHabitEntity(
                                habitId = habitId,
                                habitUid = habitUid,
                                currentDate = d.toString(),
                                completed = false
                            )
                        )
                        d = d.plusDays(1)
                    }
                }

                // Insert in batch (IGNORE + unique index prevents duplicates)
                habitRepository.insertHabitDates(toInsert)
            }
        }
    }
}