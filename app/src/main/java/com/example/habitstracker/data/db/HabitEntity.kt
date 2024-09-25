package com.example.habitstracker.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.habitstracker.ui.screens.today_main.toHex
import com.example.habitstracker.ui.theme.blueColor

const val TABLE_NAME = "habit_table"

@Entity(tableName = TABLE_NAME)
data class HabitEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val icon: String = "",
    val isDone: Boolean = false,
    val colorHex: String = blueColor.toHex(),
    val days: String = "", // Which days of the week to show a habit (for ex. Mon, wed, Fri,...)
    val executionTime: String = "", // There are 4 variants: Doesn`t matter, Morning, Day, Evening
    val reminder: Boolean = false // Advanced setting for habit
)
