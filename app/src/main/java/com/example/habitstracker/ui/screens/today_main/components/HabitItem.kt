package com.example.habitstracker.ui.screens.today_main.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.ui.custom.CustomCheckbox
import com.example.habitstracker.ui.theme.AppTheme

@Preview(showSystemUi = true)

@Composable
fun HabitItemPreview() {
    AppTheme(darkTheme = true) {
        HabitItem()
    }
}

@Composable
fun HabitItem(
    modifier: Modifier = Modifier,
    habitName: String = "Health eating",
) {

    val itemHeight: Dp = 100.dp

    Box(modifier = modifier.fillMaxSize()) {

        Row(
            modifier = modifier.fillMaxWidth(0.95f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = modifier
                    .height(itemHeight)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CustomCheckbox()
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
                    /*
                    * if done Color(0xFF313747)
                    * else listOf() {
                    * Color(0xFF3176ff),
                    * Color(0xFFf56936),
                    * Color(0xFFc43a3a)
                    * }
                    * */
                    containerColor = Color(0xFF313747),
                )
            ) {
                Box(
                    modifier = modifier.fillMaxSize(),
                ) {

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
                            painter = painterResource(id = R.drawable.food),
                            contentDescription = "Icon of habit",
                        )

                        Column(
                            modifier = modifier.padding(end = 30.dp)
                        ) {
                            Text(
                                modifier = modifier.padding(bottom = 10.dp),
                                text = habitName,
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall,
                            )

                            Text(
                                text = "execution status",
                                fontSize = 14.sp //Failed, Completed
                            )
                        }

                        IconButton(
                            onClick = { /*TODO*/ },
                            Modifier
                                .fillMaxHeight()
                        ) {
                            Icon(
                                modifier = modifier.fillMaxHeight(),
                                tint = Color.White.copy(alpha = 0.75f),
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
