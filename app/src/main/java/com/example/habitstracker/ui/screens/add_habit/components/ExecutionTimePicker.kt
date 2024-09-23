package com.example.habitstracker.ui.screens.add_habit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import com.example.habitstracker.ui.theme.screenContainerBackgroundDark
import com.example.habitstracker.utils.clickWithRipple

@Composable
fun ExecutionTimePicker(modifier: Modifier = Modifier) {
    Text(
        text = "Executing time",
        fontFamily = PoppinsFontFamily,
        fontSize = 13.sp,
        color = Color.White.copy(alpha = 0.50f),
    )

    Spacer(modifier = modifier.height(12.dp))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(125.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color = Color.Transparent),
    ) {
        val doesntMatterText = stringResource(id = R.string.doesnt_matter_create_habit)
        val morningText = stringResource(id = R.string.morning_create_habit)
        val dayText = stringResource(id = R.string.day_create_habit)
        val eveningText = stringResource(id = R.string.evening_create_habit)

        var selectedOption by remember {
            mutableStateOf(doesntMatterText)
        }

        ExecutionTimeItem(
            modifier.align(Alignment.TopStart),
            text = stringResource(R.string.doesnt_matter_create_habit),
            isSelected = selectedOption == doesntMatterText,
            onClick = { selectedOption = doesntMatterText }
        )

        ExecutionTimeItem(
            modifier = modifier.align(Alignment.TopEnd),
            text = stringResource(R.string.morning_create_habit),
            icon = Icons.Outlined.WbTwilight,
            isSelected = selectedOption == morningText,
            onClick = { selectedOption = morningText }
        )

        ExecutionTimeItem(
            modifier = modifier.align(Alignment.BottomStart),
            text = stringResource(R.string.day_create_habit),
            icon = Icons.Outlined.WbSunny,
            isSelected = selectedOption == dayText,
            onClick = { selectedOption = dayText }
        )

        ExecutionTimeItem(
            modifier = modifier.align(Alignment.BottomEnd),
            text = stringResource(R.string.evening_create_habit),
            icon = Icons.Outlined.DarkMode,
            isSelected = selectedOption == eveningText,
            onClick = { selectedOption = eveningText }
        )
    }
}

@Composable
private fun ExecutionTimeItem(
    modifier: Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector? = null,
) {

    Row(
        modifier = modifier
            .fillMaxWidth(0.48f)
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                color = if (!isSelected) screenContainerBackgroundDark
                else MaterialTheme.colorScheme.primaryContainer
            )
            .clickWithRipple(color = Color.White) {
                onClick.invoke()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null)
        }

        Text(
            modifier = modifier.padding(start = 6.dp),
            text = text,
            fontFamily = PoppinsFontFamily,
            fontSize = 13.sp,
            color = Color.White,
        )

    }
}