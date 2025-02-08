package com.example.habitstracker.habit.presentation.add_habit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.rounded.ArrowCircleRight
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.SentimentVerySatisfied
import androidx.compose.material.icons.rounded.SportsFootball
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark

@Composable
fun DefaultHabitGroupItem(item: DefaultHabitGroupItem) {
    Card(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(size = 12.dp))
            .height(IntrinsicSize.Max)
            .fillMaxWidth(0.9f)
            .clickable {
                /*TODO()*/
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 60.dp,
            pressedElevation = 26.dp
        ),
        colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark)
    )
    {
        val verticalPadding = 6.dp
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = verticalPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .size(32.dp),
                    tint = item.iconColor,
                    imageVector = item.icon,
                    contentDescription = "Eat",
                )

                Column(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 6.dp),
                        text = item.name,
                        fontSize = 22.sp,
                        color = Color.White.copy(alpha = 0.95f),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                    )

                    Text(
                        modifier = Modifier.padding(),
                        text = item.describe,
                        fontSize = 13.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Icon(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(38.dp),
                    tint = Color.White.copy(alpha = 0.6f),
                    imageVector = Icons.Rounded.ArrowCircleRight,
                    contentDescription = "More about habit"
                )

            }
        }
    }
}
data class DefaultHabitGroupItem(
    val name: String,
    val describe: String,
    val icon: ImageVector,
    val iconColor: Color
)

val defaultsHabitsGroupList = listOf(
    DefaultHabitGroupItem(
        name = "Keep active & get fit",
        describe = "Sweet never lies",
        icon = Icons.Rounded.SportsFootball,
        iconColor = Color(0xFFC42310)
    ),
    DefaultHabitGroupItem(
        name = "Eat & drink healthily",
        describe = "Stick to healthy eating",
        icon = Icons.Rounded.Restaurant,
        iconColor = Color(0xFF1BBCC2).copy(0.9f)
    ),
    DefaultHabitGroupItem(
        name = "Ease stress",
        describe = "Your efforts deserve a break",
        icon = Icons.Rounded.SentimentVerySatisfied,
        iconColor = Color(0xFF5AB91F)
    ),
    DefaultHabitGroupItem(
        name = "Gain self-discipline",
        describe = "Take control of your own self-management",
        icon = Icons.Filled.BackHand,
        iconColor = Color(0xFFD94141)
    ),
    DefaultHabitGroupItem(
        name = "Leisure moments",
        describe = "Live your life to the max",
        icon = Icons.Filled.SportsEsports,
        iconColor = Color(0xFF9CCC65)
    ),
    DefaultHabitGroupItem(
        name = "Before sleep routine",
        describe = "May your dream be sweat tonight",
        icon = Icons.Filled.Bedtime,
        iconColor = Color(0xFF236ADC)
    ),
)