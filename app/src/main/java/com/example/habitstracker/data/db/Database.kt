package com.example.habitstracker.data.db

import androidx.room.Database

@Database(entities = [HabitEntity::class], version = 1)
abstract class Database {
}