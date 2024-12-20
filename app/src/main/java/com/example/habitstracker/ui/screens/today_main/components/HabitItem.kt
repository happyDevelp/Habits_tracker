package com.example.habitstracker.ui.screens.today_main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.data.db.HabitEntity
import com.example.habitstracker.ui.custom.CustomCheckbox
import com.example.habitstracker.ui.screens.today_main.iconByName
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.notSelectedColor
import com.example.habitstracker.utils.HABIT_ITEM
import com.example.habitstracker.utils.getColorFromHex

@Composable
fun HabitItem(
    modifier: Modifier = Modifier,
    habit: HabitEntity = HabitEntity(),
    onUpdateSelectedState: (id: Int, isDone: Boolean) -> Unit = { id, isDone -> }
) {

    var isDone by remember { mutableStateOf(habit.isDone) }
    val itemHeight: Dp = 90.dp
    val selectedAlpha: Float = 0.75f

    val color = habit.colorHex.getColorFromHex()

    val currentColor by animateColorAsState(
        targetValue = if (isDone) notSelectedColor else color, label = "habit selected state"
    )

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier.fillMaxWidth(0.95f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = modifier
                    .height(itemHeight)
                    .weight(1f)
                    .testTag(HABIT_ITEM),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CustomCheckbox(
                    _isChecked = isDone,
                    habit = habit,
                    onClick = {
                    isDone = !isDone
                    onUpdateSelectedState(habit.id, isDone)
                })
            }

            Card(
                modifier = modifier
                    .clip(RoundedCornerShape(size = 20.dp))
                    .height(itemHeight)
                    .fillMaxWidth()
                    .clickable { /*TODO()*/ }
                    .weight(4f),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 60.dp,
                    pressedElevation = 26.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = currentColor
                )
            ) {
                Box(modifier = modifier.fillMaxSize()) {
                    Row(
                        modifier = modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = modifier
                                .padding(start = 20.dp)
                                .size(32.dp),
                            tint = Color.White.copy(alpha = 0.90f),
                            imageVector = iconByName(habit.iconName),
                            contentDescription = stringResource(R.string.icon_of_habit_description),
                        )

                        Column(
                            modifier = modifier.padding(end = 30.dp),
                            verticalArrangement =  Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier.padding(bottom = if (!isDone) 0.dp else 10.dp),
                                text = habit.name,
                                fontSize = 20.sp,
                                color = if (!isDone) Color.White else Color.White.copy(
                                    selectedAlpha
                                ),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall,
                            )

                            AnimatedVisibility(visible = isDone) { // Hide the second text when selected
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = modifier.size(18.dp),
                                        tint = Color.White.copy(selectedAlpha),
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(4.dp))
                                    Text(
                                        text = stringResource(R.string.finished),
                                        fontSize = 14.sp,
                                        color = Color.White.copy(selectedAlpha)
                                    )
                                }
                            }
                        }

                        IconButton(
                            onClick = { /*TODO*/ },
                            Modifier.fillMaxHeight()
                        ) {
                            Icon(
                                modifier = modifier.fillMaxHeight(),
                                tint = Color.White.copy(alpha = selectedAlpha),
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More about habit"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable @Preview(showSystemUi = true)
private fun Preview() {
    AppTheme(darkTheme = true) { HabitItem() }
}