package com.example.habitstracker.habit.presentation.me.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.MyText

@Composable
fun SettingsButtonItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconBackground: Color,
    text: String,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { /*TODO*/ },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Box(
            modifier = Modifier
                .padding(start = 20.dp)
                .size(36.dp)
                .drawBehind {
                    drawRoundRect(color = iconBackground, cornerRadius = CornerRadius(8.dp.toPx()))
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(modifier = modifier.width(20.dp))
        MyText(text = text, textSize = 19.sp)
    }
    HorizontalDivider(thickness = 0.2.dp, color = Color.White)
}

data class ButtonItem(
    val icon: ImageVector,
    val iconBackground: Color,
    val text: String,
)