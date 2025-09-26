package com.example.habitstracker.habit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.data.db.HistoryDAO
import com.example.habitstracker.history.domain.AchievementEntity

@Database(
    entities = [HabitEntity::class, DateHabitEntity::class, AchievementEntity::class, /*StatisticEntity::class*/],
    exportSchema = true,
    version = 16
)
abstract class HabitDatabase : RoomDatabase() {
    abstract val habitDao: HabitDao
    abstract val historyDao: HistoryDAO

    /** Moved to [com.example.habitstracker.di.AppModule] using DI */
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

val MIGRATION_15_16= object : Migration(15, 16) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS AchievementEntity")
        database.execSQL("DROP TABLE IF EXISTS statistic_table")
    }
}