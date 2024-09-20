package com.example.habitstracker

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.ui.screens.create_own_habit.CreateOwnHabitScreen
import org.junit.Rule
import org.junit.Test

class TodayScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun myFirstTest() {

        val morningButton = hasText("Morning") and hasClickAction()
        val dayButton = hasText("Day") and hasClickAction()
        val eveningButton = hasText("Evening") and hasClickAction()

        rule.setContent {
            val mockNavController = rememberNavController()
            CompositionLocalProvider(value = LocalNavController provides mockNavController) {
                CreateOwnHabitScreen()
            }
        }

        rule.onNode(morningButton).performClick()
        rule.onNode(dayButton).performClick()
        rule.onNode(eveningButton).performClick()


    }
}