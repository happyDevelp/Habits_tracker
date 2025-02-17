package com.example.habitstracker.habit.presentation.add_habit.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.habitstracker.R
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.utils.TestTags
import com.example.habitstracker.habit.domain.HabitEntity
import kotlinx.coroutines.launch

@Composable
fun CreateButton(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    habit: HabitEntity,
    onAddHabitClick: (habit: HabitEntity) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        modifier = modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth(0.9f)
            .height(60.dp)
            .border(
                1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(corner = CornerSize(50.dp))
            ),

        enabled = habit.name.length >= 4,

        onClick = {
            coroutineScope.launch {
                onAddHabitClick(habit)
                navController.popBackStack(Route.Today, false)
            }
        },

        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color.White,
            disabledContainerColor = Color.White.copy(0.05f),
            disabledContentColor = Color.White.copy(0.6f)
        ),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.add_habit),
            fontSize = 20.sp,
        )
    }
}