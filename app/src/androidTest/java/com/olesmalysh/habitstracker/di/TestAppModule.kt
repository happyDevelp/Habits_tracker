/*
package com.example.habitstracker.di

import android.content.Context
import androidx.room.Room
import com.example.habitstracker.habit.data.db.DAO
import com.example.habitstracker.habit.data.db.HabitDatabase
import com.example.habitstracker.habit.domain.HabitRepository
import com.example.habitstracker.habit.domain.DefaultHabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Singleton
    @Provides
    fun provideInMemoryDatabase(@ApplicationContext context: Context): HabitDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            HabitDatabase::class.java
        )*/
/*.allowMainThreadQueries()*//*
.build()
    }

    @Singleton
    @Provides
    fun provideDao(database: HabitDatabase): DAO {
        return database.dao
    }

    @Singleton
    @Provides
    fun provideRepository(dao: DAO): HabitRepository {
        return RepositoryImpl(dao)
    }
}*/
