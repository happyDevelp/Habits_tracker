package com.example.habitstracker.ui.screens.add_habit.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.habitstracker.R
import com.example.habitstracker.navigation.bottombar.BottomBarScreens

@Composable
fun CreateButton(
    modifier: Modifier,
    navController: NavHostController,
) {
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

        onClick = {
            navController.navigate(BottomBarScreens.TodayScreen.name)
        },

        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.create_button),
            fontSize = 20.sp,
            color = Color.White
        )
    }
}