package com.olesmalysh.habitstracker.di

import android.content.Context
import androidx.room.Room
import com.olesmalysh.habitstracker.habit.data.db.HabitDao
import com.olesmalysh.habitstracker.habit.data.db.HabitDatabase
import com.olesmalysh.habitstracker.habit.data.repository.DefaultHabitRepository
import com.olesmalysh.habitstracker.habit.domain.HABIT_TABLE_NAME
import com.olesmalysh.habitstracker.habit.domain.HabitRepository
import com.olesmalysh.habitstracker.history.data.db.HistoryDAO
import com.olesmalysh.habitstracker.history.data.repository.DefaultHistoryRepository
import com.olesmalysh.habitstracker.history.domain.HistoryRepository
import com.olesmalysh.habitstracker.profile.data.DefaultSyncRepository
import com.olesmalysh.habitstracker.profile.data.local.LocalSyncRepository
import com.olesmalysh.habitstracker.profile.data.local.AppPreferences
import com.olesmalysh.habitstracker.profile.data.remote.CloudSyncRepository
import com.olesmalysh.habitstracker.profile.data.remote.firebase.UserFirebaseDataSource
import com.olesmalysh.habitstracker.profile.data.repository.FriendsRepositoryImpl
import com.olesmalysh.habitstracker.profile.data.repository.UserProfileRepositoryImpl
import com.olesmalysh.habitstracker.profile.data.repository.UserStatsRepositoryImpl
import com.olesmalysh.habitstracker.profile.domain.repository.FriendsRepository
import com.olesmalysh.habitstracker.profile.domain.repository.UserProfileRepository
import com.olesmalysh.habitstracker.profile.domain.repository.UserStatsRepository
import com.olesmalysh.habitstracker.profile.domain.SyncRepository
import com.olesmalysh.habitstracker.profile.presentation.sign_in.GoogleAuthUiClient
import com.olesmalysh.habitstracker.profile.presentation.sync.SyncManager
import com.olesmalysh.habitstracker.statistic.data.db.StatisticDao
import com.olesmalysh.habitstracker.statistic.data.repository.DefaultStatisticRepository
import com.olesmalysh.habitstracker.statistic.domain.StatisticRepository
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
    fun providerLocalSyncRepository(
        habitDao: HabitDao,
        historyDAO: HistoryDAO
    ): LocalSyncRepository {
        return LocalSyncRepository(habitDao, historyDAO)
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
    fun provideUserFirebaseDataSource(
        firestore: FirebaseFirestore
    ): UserFirebaseDataSource = UserFirebaseDataSource(firestore)

    @Provides
    @Singleton
    fun provideSyncRepository(
        local: LocalSyncRepository,
        cloud: CloudSyncRepository
    ): SyncRepository {
        return DefaultSyncRepository(local = local, cloud = cloud)
    }

    @Singleton
    @Provides
    fun provideUserProfileRepository(firebaseDataSource: UserFirebaseDataSource): UserProfileRepository =
        UserProfileRepositoryImpl(firebaseDataSource)

    @Singleton
    @Provides
    fun provideUserStatsRepository(firebaseDataSource: UserFirebaseDataSource): UserStatsRepository =
        UserStatsRepositoryImpl(firebaseDataSource)

    @Singleton
    @Provides
    fun provideFriendsRepository(firebaseDataSource: UserFirebaseDataSource): FriendsRepository =
        FriendsRepositoryImpl(firebaseDataSource)

    @Provides
    @Singleton
    fun provideSyncManager(
        syncRepository: SyncRepository,
        googleAuthUiClient: GoogleAuthUiClient,
        @ApplicationContext context: Context
    ): SyncManager {
        return SyncManager(syncRepository, googleAuthUiClient, context)
    }

    @Provides
    @Singleton
    fun provideSyncPreferences(
        @ApplicationContext context: Context
    ): AppPreferences = AppPreferences(context)

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
