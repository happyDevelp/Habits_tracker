package com.example.habitstracker.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

fun List<LocalDate>.chunked(size: Int): List<List<LocalDate>> {
    return this.withIndex().groupBy { it.index / size }.values.map { it.map { it.value } }
}


@RequiresApi(Build.VERSION_CODES.O)
fun generateDateSequence(startDate: LocalDate, daysCount: Int): List<LocalDate> {
    return List(daysCount) { startDate.plusDays(it.toLong()) }
}