package com.example.habitstracker.habit.presentation.add_habit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.habitstracker.R
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.utils.clickWithRipple

@Composable
fun RepeatPickerContainer(
    modifier: Modifier = Modifier,
    selectedDays: String,
    navController: NavHostController,
) {
    var selectedDaysText by remember {
        mutableStateOf(selectedDays)
    }

    Text(
        text = "REPEAT (Long-term habits)",
        fontFamily = PoppinsFontFamily,
        fontSize = 13.sp,
        color = Color.White.copy(alpha = 0.50f),
    )

    Spacer(modifier = modifier.height(12.dp))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color = screenContainerBackgroundDark)
            .clickWithRipple(
                color = Color.White
            ) { navController.navigate(Route.EditRepeatPicker) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier.padding(start = 16.dp),
            text = stringResource(R.string.days_of_habits),
            fontFamily = PoppinsFontFamily,
            fontSize = 14.sp,
            color = Color.White,
        )

        Row(
            modifier = modifier.padding(end = 12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = selectedDaysText,
                fontFamily = PoppinsFontFamily,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.5f),
            )

            Spacer(modifier = modifier.width(12.dp))
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.White.copy(0.7f)
            )
        }
    }
}