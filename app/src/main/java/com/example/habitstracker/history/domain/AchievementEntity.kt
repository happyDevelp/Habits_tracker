package com.example.habitstracker.history.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievement_table")
data class AchievementEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val section: String,  // "Habits Finished", "Perfect Days", "Best Streak"
    val target: Int, // 1, 10, 25, 30, 100 ...
    val unlockedAt: String, // Date when received
    val isNotified: Boolean = false // shown a achievnotification or not
)