package com.example.habitstracker.history.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievement_table")
data class AchievementEntity (
    @PrimaryKey val id: Int = 0,
    val section: String = "none",  // "Habits Finished", "Perfect Days", "Best Streak"
    val target: Int = 0, // 1, 10, 25, 30, 100 ...
    val unlockedAt: String = "none", // Date when received
    val notified: Boolean = false // shown a achievnotification or not
)

val achievementsList: List<AchievementEntity> = listOf(
    /** Habits Finished */
    AchievementEntity(0, "Habits Finished", 1, "none"),
    AchievementEntity(1, "Habits Finished", 10, "none"),
    AchievementEntity(2, "Habits Finished", 25, "none"),
    AchievementEntity(3, "Habits Finished", 100, "none"),
    AchievementEntity(4, "Habits Finished", 500, "none"),
    AchievementEntity(5, "Habits Finished", 1000, "none"),

    /** Perfect Days */
    AchievementEntity(6, "Perfect Days", 3, "none"),
    AchievementEntity(7, "Perfect Days", 10, "none"),
    AchievementEntity(8, "Perfect Days", 25, "none"),
    AchievementEntity(9, "Perfect Days", 50, "none"),
    AchievementEntity(10, "Perfect Days", 100, "none"),
    AchievementEntity(11, "Perfect Days", 250, "none"),

    /** Best Streak */
    AchievementEntity(12, "Best Streak", 3, "none"),
    AchievementEntity(13, "Best Streak", 5, "none"),
    AchievementEntity(14, "Best Streak", 10, "none"),
    AchievementEntity(15, "Best Streak", 25, "none"),
    AchievementEntity(16, "Best Streak", 50, "none"),
    AchievementEntity(17, "Best Streak", 100, "none"),
)