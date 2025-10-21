package com.example.habitstracker.habit.presentation.detail_habit.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowCircleRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.utils.getIconName
import com.example.habitstracker.core.presentation.utils.toHex

@Composable
fun DefaultHabitDetailItem(item: DefaultHabitDetailItem) {
    val navController = LocalNavController.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                navController.navigate(
                    Route.CreateHabit(
                        name = item.name,
                        icon = getIconName(item.icon),
                        iconColor = item.iconColor.toHex()
                    )
                )
            },
        colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark)
    )
    {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding( horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    tint = item.iconColor,
                    imageVector = item.icon,
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 12.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier.padding(top = 4.dp, bottom = 2.dp),
                        text = item.name,
                        fontSize = 17.sp,
                        color = Color.White.copy(alpha = 0.88f),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
        }
    }
}

data class DefaultHabitDetailItem(
    val name: String,
    val describe: String,
    val icon: ImageVector,
    val iconColor: Color
)

fun getGroupDetails(groupName: String, context: Context): List<DefaultHabitDetailItem> {
    return when (groupName) {
        UiText.StringResources(R.string.get_fit).asString(context) -> keepActiveGetFit
        UiText.StringResources(R.string.eat_drink_healthily).asString(context) -> eatDrinkHealthily
        UiText.StringResources(R.string.entertainment).asString(context) -> easeStress
        UiText.StringResources(R.string.self_discipline)
            .asString(context) -> gainSelfDiscipline

        UiText.StringResources(R.string.leisure_moments).asString(context) -> leisureMoments
        UiText.StringResources(R.string.good_morning).asString(context) -> goodMorningHabits
        UiText.StringResources(R.string.before_sleep)
            .asString(context) -> beforeSleepRoutineHabits

        UiText.StringResources(R.string.productivity)
            .asString(context) -> masterProductivityHabits

        UiText.StringResources(R.string.stronger_mind).asString(context) -> strongerMindHabits

        else -> throw Exception("In 'DefaultHabitDetailsItem' screen there are unknown entered data in 'getGroupDetails' function")
    }
}