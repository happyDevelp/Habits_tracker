package com.example.habitstracker.app

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}

class SettingsSheetController {
    var isOpen by mutableStateOf(false)
        private set

    fun open() { isOpen = true }
    fun close() { isOpen = false }
}

val LocalSettingsSheetController =
    staticCompositionLocalOf<SettingsSheetController> { error("No controller") }