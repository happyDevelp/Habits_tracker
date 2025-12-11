package com.example.habitstracker.statistic

import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.statistic.presentation.components.getCurrentStreak
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate

class StatisticUtilsTest {

    private fun dateHabit(
        date: LocalDate,
        completed: Boolean
    ): DateHabitEntity {
        // set real fields
        return DateHabitEntity(
            id = 0,
            habitId = 1,
            currentDate = date.toString(),
            completed = completed,
        )
    }

    @Test
    fun getCurrentStreak_allHabitsCompletedForFirst3Days_returns3() {
        val today = LocalDate.of(2025, 12, 9)

        val list = listOf(
            // today – 2 habits, both fulfilled
            dateHabit(today, true),
            dateHabit(today, true),

            // yesterday – all fulfilled
            dateHabit(today.minusDays(1), true),
            dateHabit(today.minusDays(1), true),

            // the day before yesterday – all of them were also fulfilled
            dateHabit(today.minusDays(2), true)
        )

        val result = getCurrentStreak(list)

        assertEquals(3, result)
    }


    @Test
    fun getCurrentStreak_someDayNotFullyCompleted_breaksStreak() {
        val today = LocalDate.of(2025, 12, 9)

        val list = listOf(
            // today – all completed
            dateHabit(today, true),
            dateHabit(today, true),

            // Yesterday - one unfulfilled → the tape should break
            dateHabit(today.minusDays(1), true),
            dateHabit(today.minusDays(1), false),

            // Further, although all are completed, the string should no longer continue
            dateHabit(today.minusDays(2), true)
        )

        val result = getCurrentStreak(list)

        assertEquals(1, result)
    }

    @Test
    fun getCurrentStreak_emptyList_returnsZero() {
        val result = getCurrentStreak(emptyList())
        assertEquals(0, result)
    }
}