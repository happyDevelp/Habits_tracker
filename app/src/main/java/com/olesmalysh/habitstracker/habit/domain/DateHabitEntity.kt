package com.olesmalysh.habitstracker.habit.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// The Domain layer is independent of data sources.

const val DATE_TABLE_NAME = "date_table"

@Entity(
    tableName = DATE_TABLE_NAME,
    foreignKeys = [
        ForeignKey(entity = HabitEntity::class, parentColumns = ["id"], childColumns = ["habitId"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = ["habitUid", "currentDate"], unique = true)
    ]
)
data class  DateHabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val habitId: Int = 0,

    @ColumnInfo(defaultValue = "''")
    val habitUid: String = "",

    val currentDate: String = "2025-01-01", // YYYY-MM-DD
    val completed: Boolean = false
)