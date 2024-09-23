package com.example.habitstracker.ui.screens.add_habit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import com.example.habitstracker.ui.theme.screenContainerBackgroundDark
import com.example.habitstracker.utils.clickWithRipple

@Composable
fun AdvancedSettings(modifier: Modifier = Modifier) {
    var advSettingsIsPressed by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickWithRipple(
                color = Color.White
            ) { advSettingsIsPressed = !advSettingsIsPressed },

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Advanced settings",
            modifier = modifier.padding(start = 8.dp),
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            fontSize = 15.sp
        )

        Icon(
            modifier = Modifier
                .padding(end = 12.dp)
                .rotate(if (advSettingsIsPressed) 95f else 0f),
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.White.copy(0.7f)
        )
    }


    AnimatedVisibility(visible = advSettingsIsPressed) {
        Spacer(modifier = modifier.height(4.dp))

        Text(
            text = "Reminder",
            fontFamily = PoppinsFontFamily,
            fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.50f),
        )

        Spacer(modifier = modifier.height(8.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(55.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(color = screenContainerBackgroundDark)
                .clickWithRipple(
                    color = Color.White
                ) { /*TODO*/ },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier.padding(start = 16.dp),
                text = stringResource(R.string.a_reminder_for_this_habit),
                fontFamily = PoppinsFontFamily,
                fontSize = 13.sp,
                color = Color.White,
            )

            var isChecked by remember {
                mutableStateOf(false)
            }

            Switch(
                modifier = modifier
                    .padding(end = 12.dp)
                    .scale(0.8f),
                checked = isChecked,
                onCheckedChange = { isChecked = !isChecked }
            )
        }
    }
}