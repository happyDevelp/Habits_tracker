package com.example.habitstracker.ui.screens.add_habit.scaffold

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAddHabitScreen() {
    SmallTopAppBar(
        title = {
            Text(
                text = "Add a new habit",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}
