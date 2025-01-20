package com.example.habitstracker.di

import android.content.Context
import androidx.room.Room
import com.example.habitstracker.habit.data.db.DAO
import com.example.habitstracker.habit.data.db.HabitDatabase
import com.example.habitstracker.habit.data.db.TABLE_NAME
import com.example.habitstracker.habit.data.db.repository.DBRepository
import com.example.habitstracker.habit.data.db.repository.RepositoryImpl
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
    fun provideRepository(dao: DAO): DBRepository {
        return RepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideDao(database: HabitDatabase): DAO {
        return database.dao
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HabitDatabase {
        return Room.databaseBuilder(
            context,
            HabitDatabase::class.java,
            TABLE_NAME
        ).build()
    }
}
