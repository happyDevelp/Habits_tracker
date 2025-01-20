package com.example.habitstracker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.habitstracker.app.MainActivity
import com.example.habitstracker.habit.data.db.DAO
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.di.AppModule
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.utils.TestTags
import com.example.habitstracker.utils.toHex
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class DatabaseTest {

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
        composeRule.onNodeWithTag(TestTags.CUSTOM_CHECK_BOX + "_DefaultHabit").performClick()

        // Wait until all background tasks (animations, work with the database...) are completed
        composeRule.awaitIdle()

        val habit = dao.getHabitByName("DefaultHabit")
        assertTrue(habit?.isDone == true)
    }

    @Test
    fun createNewHabit_isExist() {
        val habitName = "Test1"
        composeRule.onNodeWithTag(TestTags.CREATE_NEW_HABIT_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.CREATE_OWN_HABIT_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.CREATEHABIT_TEXT_FIELD).performTextInput(habitName)
        composeRule.onNodeWithTag(TestTags.BUTTON_CREATE_OWN_HABIT_SCREEN).performClick()

        composeRule.onNodeWithText(habitName).assertExists()
    }

    @Test
    fun deleteHabit_isNotExist() = runTest {
        val habitName = "DefaultHabit"
        val habit: HabitEntity? = dao.getHabitByName(habitName)

        composeRule.onNodeWithText(habitName).assertIsDisplayed()
        if (habit != null)
            dao.deleteHabit(entity = habit)
        else throw IllegalStateException("Habit with name $habitName does not exist")

        composeRule.awaitIdle()
        composeRule.onNodeWithTag(habitName).assertDoesNotExist()
    }

    /*@Test
    fun createNewHabitAndUpdateAfterwards_isUpdated() = runTest {
        val habitName = "test_habit"
        composeRule.onNodeWithTag(TestTags.CREATE_NEW_HABIT_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.CREATE_OWN_HABIT_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.CREATEHABIT_TEXT_FIELD).performTextInput(habitName)
        composeRule.onNodeWithTag(TestTags.BUTTON_CREATE_OWN_HABIT_SCREEN).performClick()

        composeRule.onNodeWithText(habitName).assertExists()
        val habit = dao.getHabitByName(habitName)

        val newHabitName = "updated_habit"
        val newHabit = HabitEntity(
            0, newHabitName, "SentimentVerySatisfied", false,
            blueColor.toHex(), "Everyday", "Anytime", false,
        )

        if (habit != null)
            dao.updateHabit(newHabit)
        else throw IllegalStateException("Habit with name $newHabitName does not exist and can`t be updated")

        composeRule.awaitIdle()

       // composeRule.onNodeWithText(habitName).assertDoesNotExist()
        composeRule.onNodeWithText(newHabitName).assertIsDisplayed()
    }*/
}