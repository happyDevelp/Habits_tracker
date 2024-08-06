package com.example.habitstracker.ui.main

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.ui.theme.buttonAddNewHabit

@Preview()
@Composable
fun HabitLayout(
    habitName: String = "Health eating",
) {
    Card(
        modifier = Modifier
            .padding(top = 20.dp)
            .clip(RoundedCornerShape(size = 20.dp))
            .height(80.dp)
            .fillMaxWidth(0.8f)
            .clickable { /*TODO()*/ }
            .background(color = buttonAddNewHabit),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 60.dp,
            pressedElevation = 26.dp
        )
        ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(32.dp),
                    tint = Color.White.copy(alpha = 0.75f),
                    painter = painterResource(id = R.drawable.food),
                    contentDescription = "Icon of habit",
                )

                Column(
                    modifier = Modifier.padding(end = 30.dp)
                ) {
                    Text(
                        text = habitName,
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontSize = 20.sp,
                        color = Color.White.copy(alpha = 0.75f),
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
                        .background(color = buttonAddNewHabit)
                        .fillMaxHeight()
                ) {
                    Icon(
                        modifier = Modifier.fillMaxHeight(),
                        tint = Color.White.copy(alpha = 0.75f),
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More about habit"
                    )

                }


            }
        }
    }
    }
