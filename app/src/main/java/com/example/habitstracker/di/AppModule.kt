package com.example.habitstracker.di

import android.content.Context
import androidx.room.Room
import com.example.habitstracker.habit.data.db.HabitDao
import com.example.habitstracker.habit.data.db.HabitDatabase
import com.example.habitstracker.habit.data.repository.DefaultHabitRepository
import com.example.habitstracker.habit.domain.HABIT_TABLE_NAME
import com.example.habitstracker.habit.domain.HabitRepository
import com.example.habitstracker.history.data.db.HistoryDAO
import com.example.habitstracker.history.data.repository.DefaultHistoryRepository
import com.example.habitstracker.history.domain.HistoryRepository
import com.example.habitstracker.me.data.DefaultSyncRepository
import com.example.habitstracker.me.data.local.LocalSyncRepository
import com.example.habitstracker.me.data.remote.CloudSyncRepository
import com.example.habitstracker.me.domain.SyncRepository
import com.example.habitstracker.me.presentation.sign_in.GoogleAuthUiClient
import com.example.habitstracker.me.presentation.sync.SyncManager
import com.example.habitstracker.statistic.data.db.StatisticDao
import com.example.habitstracker.statistic.data.repository.DefaultStatisticRepository
import com.example.habitstracker.statistic.domain.StatisticRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providerLocalSyncRepository(habitDao: HabitDao): LocalSyncRepository {
        return LocalSyncRepository(habitDao)
    }

    @Provides
    @Singleton
    fun provideCloudSyncRepository(
        firestore: FirebaseFirestore
    ): CloudSyncRepository {
        return CloudSyncRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideSyncRepository(
        local: LocalSyncRepository,
        cloud: CloudSyncRepository
    ): SyncRepository {
        return DefaultSyncRepository(local = local, cloud = cloud)
    }

    @Provides
    @Singleton
    fun provideSyncManager(
        syncRepository: SyncRepository,
        googleAuthUiClient: GoogleAuthUiClient,
        @ApplicationContext context: Context
    ): SyncManager {
        return SyncManager(syncRepository, googleAuthUiClient, context)
    }

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
    fun provideStatisticRepository(statisticDao: StatisticDao): StatisticRepository {
        return DefaultStatisticRepository(statisticDao)
    }

    @Singleton
    @Provides
    fun provideStatisticDao(database: HabitDatabase): StatisticDao {
        return database.statisticDao
    }

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(
        @ApplicationContext context: Context
    ): GoogleAuthUiClient = GoogleAuthUiClient(context)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HabitDatabase {
        return Room.databaseBuilder(
            context,
            HabitDatabase::class.java,
            name = HABIT_TABLE_NAME
        )
            //.addMigrations(MIGRATION_15_16)
            .fallbackToDestructiveMigration()
            .build()
    }
}
