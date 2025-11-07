package com.example.habitstracker.history.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.utils.TestTags
import com.example.habitstracker.core.presentation.utils.getColorFromHex
import com.example.habitstracker.core.presentation.utils.getGradientByLightColor
import com.example.habitstracker.core.presentation.utils.iconByName
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.statistic.presentation.rolling30DayConsistency


@Composable
fun HistoryHabitItem(
    modifier: Modifier = Modifier,
    habit: HabitEntity,
    allDateHabits: List<DateHabitEntity>,
    onDeleteClick: (habit: HabitEntity) -> Unit
) {
    val navController = LocalNavController.current
    val itemHeight: Dp = 85.dp
    val selectedAlpha = 0.75f

    val color = getGradientByLightColor(habit.colorHex.getColorFromHex())

    var isMenuExpanded by remember { mutableStateOf(false) }

    val targetGradient = color


    val specificHabitConsistency = rolling30DayConsistency(
        allDateHabits.filter { it.habitId == habit.id }
    )


    // Animation of both colors separately
    val animatedLight by animateColorAsState(
        targetValue = targetGradient.light,
        label = "light gradient animation"
    )

    val animatedDark by animateColorAsState(
        targetValue = targetGradient.dark,
        label = "dark gradient animation"
    )

    Box(modifier = modifier.wrapContentSize()) {
        Row(
            modifier = modifier.padding(end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = modifier
                    .height(itemHeight)
                    .weight(0.7f)
                    .testTag(TestTags.HABIT_ITEM),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { isMenuExpanded = !isMenuExpanded },
                    Modifier.fillMaxHeight()
                ) {
                    Icon(
                        modifier = modifier.fillMaxHeight(),
                        tint = Color.White.copy(alpha = selectedAlpha),
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More about habit"
                    )

                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false },
                    ) {

                        DropdownMenuItem(
                            text = { Text(text = "Edit") },
                            trailingIcon = { Icons.Default.Edit },
                            onClick = {
                                isMenuExpanded = false
                                navController.navigate(
                                    Route.CreateHabit(
                                        id = habit.id,
                                        name = habit.name,
                                        icon = habit.iconName,
                                        iconColor = habit.colorHex,
                                        isEditMode = true
                                    )
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = { Text(text = "Delete") },
                            trailingIcon = { Icons.Default.Delete },
                            onClick = {
                                isMenuExpanded = false
                                onDeleteClick.invoke(habit)
                            }
                        )
                    }
                }
            }

            Card(
                modifier = modifier
                    .clip(RoundedCornerShape(size = 20.dp))
                    .height(itemHeight)
                    .fillMaxWidth()
                    .clickable { }
                    .weight(4f),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 60.dp,
                    pressedElevation = 26.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(animatedLight, animatedDark),
                                center = Offset(120f, 20f),
                                radius = 700f // Distribution radius
                            )
                        )
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = Color.White.copy(alpha = 0.2f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                tint = Color.White.copy(alpha = 0.90f),
                                imageVector = iconByName(habit.iconName),
                                contentDescription = stringResource(R.string.icon_of_habit_description),
                            )
                        }

                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier.padding(0.dp),
                                text = habit.name,
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }


                        Row(
                            modifier = modifier
                                .wrapContentHeight()
                                .testTag(TestTags.HABIT_ITEM),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = modifier.padding(end = 4.dp),
                                text = "$specificHabitConsistency%",
                                fontSize = 22.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = false)
@Composable
private fun HabitItemPreview() {
    CompositionLocalProvider(
        LocalNavController provides rememberNavController()
    ) {
        AppTheme(darkTheme = true) {
            HistoryHabitItem(
                habit = HabitEntity(),
                modifier = Modifier,
                onDeleteClick = {},
                allDateHabits = listOf()
            )
        }
    }
}