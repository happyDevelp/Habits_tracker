package com.example.habitstracker.habit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.DateHabitEntity

@Database(entities = [HabitEntity::class, DateHabitEntity::class], exportSchema = true, version = 9)
abstract class HabitDatabase: RoomDatabase() {
    abstract val dao: DAO

    // Moved to AppModule using DI
   /* companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    TABLE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }*/
}