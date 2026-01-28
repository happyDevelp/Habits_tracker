package com.olesmalysh.habitstracker.core.filling_habits

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.ZonedDateTime

@HiltWorker
class DailyRolloverWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val fillMissingDatesUseCase: FillMissingDatesUseCase
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            Log.d("DailyRolloverWorker", "doWork started at=${ZonedDateTime.now()}")

            //fillMissingDatesUseCase()

            Log.d("DailyRolloverWorker", "fillMissingDates finished at=${ZonedDateTime.now()}")

            // Schedule next run
           // DailyRolloverScheduler.schedule(applicationContext)

            Result.success()
        } catch(t: Throwable) {

            Log.e("DailyRolloverWorker", "doWork failed at=${ZonedDateTime.now()}", t)

            Result.retry()
        }
    }
}