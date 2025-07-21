package com.example.habitstracker.di

import android.content.Context
import androidx.room.Room
import com.example.habitstracker.habit.data.db.HabitDao
import com.example.habitstracker.habit.data.db.HabitDatabase
import com.example.habitstracker.habit.domain.HABIT_TABLE_NAME
import com.example.habitstracker.habit.data.repository.DefaultHabitRepository
import com.example.habitstracker.habit.domain.HabitRepository
import com.example.habitstracker.history.data.db.HistoryDAO
import com.example.habitstracker.history.data.repository.DefaultHistoryRepository
import com.example.habitstracker.history.domain.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHabitRepository(habitDao: HabitDao): HabitRepository {
        return DefaultHabitRepository(habitDao)
    }

    @Singleton
    @Provides
    fun provideHabitDao(database: HabitDatabase): HabitDao {
        return database.habitDao
    }

    @Singleton
    @Provides
    fun provideHistoryRepository(historyDao: HistoryDAO): HistoryRepository {
        return DefaultHistoryRepository(historyDao)
    }

    @Singleton
    @Provides
    fun provideHistoryDao(database: HabitDatabase): HistoryDAO {
        return database.historyDao
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HabitDatabase {
        return Room.databaseBuilder(
            context,
            HabitDatabase::class.java,
            name = HABIT_TABLE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
