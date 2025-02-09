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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark

@Composable
fun DefaultHabitGroupItem(item: DefaultHabitGroupItem) {
    val navController = LocalNavController.current
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(size = 8.dp))
            .height(IntrinsicSize.Max)
            .fillMaxWidth(0.96f)
            .clickable {
                navController.navigate(
                    Route.GroupHabit(
                        groupName = item.name.asString(context),
                        groupDescribe = item.describe.asString(context)
                    )
                )
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 60.dp,
            pressedElevation = 26.dp
        ),
        colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark)
    )
    {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .size(34.dp),
                    tint = item.iconColor,
                    imageVector = item.icon,
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = item.name.asString(),
                        fontSize = 19.sp,
                        color = Color.White.copy(alpha = 0.90f),
                        fontFamily = PoppinsFontFamily
                    )

                    Text(
                        text = item.describe.asString(),
                        fontSize = 13.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp
                    )
                }

                Icon(
                    modifier = Modifier
                        .padding(end = 14.dp)
                        .size(36.dp),
                    tint = Color.White.copy(alpha = 0.5f),
                    imageVector = Icons.Rounded.ArrowCircleRight,
                    contentDescription = "More about habit"
                )

            }
        }
    }
}

data class DefaultHabitGroupItem(
    val name: UiText,
    val describe: UiText,
    val icon: ImageVector,
    val iconColor: Color
)

val defaultsHabitsGroupList = listOf(
    DefaultHabitGroupItem(
        name = UiText.StringResources(resId = R.string.keep_active_get_fit),
        describe = UiText.DynamicString("Sweet never lies"),
        icon = Icons.Rounded.SportsFootball,
        iconColor = Color(0xFFE13C0B)
    ),
    DefaultHabitGroupItem(
        name = UiText.StringResources(R.string.eat_drink_healthily),
        describe = UiText.DynamicString("Stick to healthy eating"),
        icon = Icons.Rounded.Restaurant,
        iconColor = Color(0xFF1BBCC2).copy(0.9f)
    ),
    DefaultHabitGroupItem(
        name = UiText.StringResources(R.string.ease_stress),
        describe = UiText.DynamicString("Your efforts deserve a break"),
        icon = Icons.Rounded.SentimentVerySatisfied,
        iconColor = Color(0xFF5AB91F)
    ),
    DefaultHabitGroupItem(
        name = UiText.StringResources(R.string.gain_self_discipline),
        describe = UiText.DynamicString("Take control of your own self-management"),
        icon = Icons.Filled.BackHand,
        iconColor = Color(0xFFD94141)
    ),
    DefaultHabitGroupItem(
        name = UiText.StringResources(R.string.leisure_moments),
        describe = UiText.DynamicString("Live your life to the max"),
        icon = Icons.Filled.SportsEsports,
        iconColor = Color(0xFF9CCC65)
    ),
    DefaultHabitGroupItem(
        name = UiText.StringResources(R.string.before_sleep_routine),
        describe = UiText.DynamicString("May your dream be sweat tonight"),
        icon = Icons.Filled.Bedtime,
        iconColor = Color(0xFF3673DE)
    ),
)