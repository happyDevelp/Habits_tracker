package com.example.habitstracker

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.habitstracker.app.MainActivity
import com.example.habitstracker.data.db.DAO
import com.example.habitstracker.data.db.HabitEntity
import com.example.habitstracker.data.di.AppModule
import com.example.habitstracker.ui.theme.blueColor
import com.example.habitstracker.utils.BUTTON_CREATE_OWN_HABIT_SCREEN
import com.example.habitstracker.utils.CREATEHABIT_TEXT_FIELD
import com.example.habitstracker.utils.CREATE_NEW_HABIT_BUTTON
import com.example.habitstracker.utils.CREATE_OWN_HABIT_BUTTON
import com.example.habitstracker.utils.CUSTOM_CHECK_BOX
import com.example.habitstracker.utils.toHex
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class MainScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    // Інжектуємо DAO через Hilt
    @Inject
    lateinit var dao: DAO


    @Before
    fun setUp() = runTest {
        hiltRule.inject()

        dao.addHabit(
            HabitEntity(
                5, "DefaultHabit", "SentimentVerySatisfied", false,
                blueColor.toHex(), "Everyday", "Anytime", false
            )
        )
    }

    @Test
    fun clickCustomCheckBox_isSelected() = runTest {
        composeRule.onNodeWithTag(CUSTOM_CHECK_BOX + "_DefaultHabit").performClick()

        // Wait until all background tasks (animations, work with the database...) are completed
        composeRule.awaitIdle()

        val habit = dao.getHabitByName("DefaultHabit")
        assertTrue(habit?.isDone == true)
    }

    @Test
    fun createNewHabit_isExist() {
        val habitName = "Test1"
        composeRule.onNodeWithTag(CREATE_NEW_HABIT_BUTTON).performClick()
        composeRule.onNodeWithTag(CREATE_OWN_HABIT_BUTTON).performClick()
        composeRule.onNodeWithTag(CREATEHABIT_TEXT_FIELD).performTextInput(habitName)
        composeRule.onNodeWithTag(BUTTON_CREATE_OWN_HABIT_SCREEN).performClick()

        composeRule.onNodeWithText(habitName).assertExists()
    }
}