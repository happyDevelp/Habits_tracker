package com.example.habitstracker.ui.screens.add_habit.components

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
import androidx.compose.material.icons.rounded.ArrowCircleRight
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.buttonAddNewHabit

@Preview(showSystemUi = true)
@Composable
fun DefaultChooseHabitItem(
    modifier: Modifier = Modifier,
    groupName: String = "Eat",
    groupDescribe: String = "default describe manu afa kotu na barkoto makoro",
    verticalPadding: Dp = 6.dp,
    icon: Painter = painterResource(id = R.drawable.food),
) {
    AppTheme(darkTheme = true) {
        Card(
            modifier = modifier
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

            colors = CardDefaults.cardColors(
                containerColor = buttonAddNewHabit,
                )
        ) {
            Box(
                modifier = modifier.fillMaxSize()
            ) {

                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(vertical = verticalPadding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        modifier = modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .size(32.dp),
                        tint = Color.White.copy(alpha = 0.75f),
                        painter = icon,
                        contentDescription = "Eat",
                    )

                    Column(
                        modifier = modifier
                            .padding(end = 12.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = modifier.padding(bottom = 6.dp),
                            text = groupName,
                            fontSize = 22.sp,
                            color = Color.White.copy(alpha = 0.95f),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall,
                        )

                        Text(
                            modifier = modifier.padding(),
                            text = groupDescribe,
                            fontSize = 13.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Icon(
                        modifier = modifier
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

}
