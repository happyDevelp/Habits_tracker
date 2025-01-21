package com.example.habitstracker.core.domain

// Not in use yet
sealed interface DataError: Error {
    enum class Local: DataError {
        CANT_FIND_HABIT
    }
}