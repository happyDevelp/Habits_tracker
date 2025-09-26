package com.example.habitstracker.habit.presentation.today_main.utility

import com.example.habitstracker.habit.domain.ShownHabit
import java.time.LocalDate

fun getBestStreak(mapHabitsToDate: Map<LocalDate, List<ShownHabit>>): Int {
    val dates = mapHabitsToDate.keys.toList().sorted()
    var currentStreak = 0
    var bestStreak = 0

    for (date in dates) {
        val habits = mapHabitsToDate[date].orEmpty()
        val isPerfectDay = habits.isNotEmpty() && habits.all { it.isSelected }

        if (isPerfectDay) {
            currentStreak++
            bestStreak = maxOf(bestStreak, currentStreak)
        } else {
            currentStreak = 0
        }
    }
    return bestStreak
}