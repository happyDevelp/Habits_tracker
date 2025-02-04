package com.example.habitstracker.habit.domain

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.core.presentation.utils.toHex

/*data class  ShownHabit (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val iconName: String = "",
    //val isCompleted: Boolean = false,
    val colorHex: String = blueColor.toHex(),
    val days: String = "", // Which days of the week to show a habit (for ex. Mon, wed, Fri,...)
    val executionTime: String = "", // There are 4 variants: Doesn`t matter, Morning, Day, Evening
    val reminder: Boolean = false // Advanced setting for habit
)*/
data class ShownHabit (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val iconName: String = "",
    //val isCompleted: Boolean = false,
    val colorHex: String = blueColor.toHex(),
    val days: String = "", // Which days of the week to show a habit (for ex. Mon, wed, Fri,...)
    val executionTime: String = "", // There are 4 variants: Doesn`t matter, Morning, Day, Evening
    val reminder: Boolean = false, // Advanced setting for habit
    val isSelected: Boolean = false
)