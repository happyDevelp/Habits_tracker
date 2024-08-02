package com.example.habitstracker.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.ui.theme.buttonAddNewHabit

@Composable
fun HabitLayout(
    habitName: String = "Health eating",
) {
    Box(
        modifier = Modifier
            .padding(top = 100.dp)
            .clip(RoundedCornerShape(size = 20.dp))
            .height(80.dp)
            .fillMaxWidth(0.7f)
            .clickable { /*TODO()*/ }
            .background(color = buttonAddNewHabit),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = habitName,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.75f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall,
            )

            IconButton(
                onClick = { /*TODO*/ },
                Modifier
                    .background(color = buttonAddNewHabit)
                    .fillMaxHeight()
            ) {
                Icon(
                    tint = Color.White.copy(alpha = 0.75f),
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )

            }


        }
    }
}