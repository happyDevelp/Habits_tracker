package com.olesmalysh.habitstracker.habit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.olesmalysh.habitstracker.habit.domain.DateHabitEntity
import com.olesmalysh.habitstracker.habit.domain.HabitEntity
import com.olesmalysh.habitstracker.history.data.db.HistoryDAO
import com.olesmalysh.habitstracker.history.domain.AchievementEntity
import com.olesmalysh.habitstracker.statistic.data.db.StatisticDao

const val DB_NAME = "habits_db"

@Database(
    entities = [HabitEntity::class, DateHabitEntity::class, AchievementEntity::class, /*StatisticEntity::class*/],
    exportSchema = true,
    version = 19
)
abstract class HabitDatabase : RoomDatabase() {
    abstract val habitDao: HabitDao
    abstract val historyDao: HistoryDAO
    abstract val statisticDao: StatisticDao

    /** Moved to [com.olesmalysh.habitstracker.di.AppModule] using DI */
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

val MIGRATION_18_19 = object : Migration(18, 19) {
    override fun migrate(db: SupportSQLiteDatabase) {

        // Step 1: Add 'uid' column to habit_table
        db.execSQL(
            """
            ALTER TABLE habit_table
            ADD COLUMN uid TEXT NOT NULL DEFAULT ''
            """.trimIndent()
        )

        // Step 2: Fill uid for existing habits
        // SQLite doesn't have a built-in UUID generator, so we create a stable legacy uid.
        // This guarantees uniqueness inside this database.
        db.execSQL(
            """
            UPDATE habit_table
            SET uid = 'legacy_' || id
            WHERE uid = ''
            """.trimIndent()
        )

        // Step 3: Ensure uid is unique (recommended)
        db.execSQL(
            """
            CREATE UNIQUE INDEX IF NOT EXISTS index_habit_table_uid
            ON habit_table(uid)
            """.trimIndent()
        )

        // Step 4: Add 'habitUid' column to date_table
        db.execSQL(
            """
            ALTER TABLE date_table
            ADD COLUMN habitUid TEXT NOT NULL DEFAULT ''
            """.trimIndent()
        )

        // Step 5: Backfill habitUid using habitId -> habit_table.uid
        db.execSQL(
            """
            UPDATE date_table
            SET habitUid = (
                SELECT uid FROM habit_table
                WHERE habit_table.id = date_table.habitId
            )
            WHERE habitUid = ''
            """.trimIndent()
        )

        // Step 6: Create unique index for stable key (habitUid + currentDate)
        db.execSQL(
            """
            CREATE UNIQUE INDEX IF NOT EXISTS index_date_table_habitUid_currentDate
            ON date_table(habitUid, currentDate)
            """.trimIndent()
        )
    }
}