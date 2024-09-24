package com.example.habitstracker.data.db

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.habitstracker.ui.theme.notSelectedColor

@Entity(tableName = TABLE_NAME)
data class HabitEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String = "",
    val icon: String = "",
    val isDone: Boolean = false,
    val color: String = notSelectedColor.toString(),
    val days: String = "", // Which days of the week to show a habit (for ex. Mon, wed, Fri,...)
    val executionTime: String = "", // There are 4 variants: Doesn`t matter, Morning, Day, Evening
    val reminder: Boolean = false // Advanced setting for habit
)
/*{
    constructor(id: Int, name: String, icon: String, isDone: Boolean, color: Color, days: String, executionTime: String, reminder: Boolean)
}*/

const val TABLE_NAME = "habit_table"