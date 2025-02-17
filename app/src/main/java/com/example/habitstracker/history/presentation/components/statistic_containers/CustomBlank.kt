package com.example.habitstracker.history.presentation.components.statistic_containers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.MyText

@Composable
fun CustomBlank(
    modifier: Modifier = Modifier,
    width: Dp = 150.dp,
    color: Color,
    topText: String,
    middleText: String,
    bottomText: String,
) {
    Card(
        modifier = modifier.size(width = width, height = 160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color),
    ) {

        var middleNumber by remember {
            mutableIntStateOf(middleText.toInt())
        }

        var bottomTextValue by remember {
            mutableIntStateOf(2)
        }
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            MyText(text = topText, textSize = 14.sp)
            Spacer(modifier = modifier.height(3.dp))
            MyText(text = middleNumber.toString(), textSize = 50.sp)
            Text(
                text = bottomText + bottomTextValue,
                fontSize = 10.5.sp,
                color = Color.White.copy(0.8f),
            )
        }
    }
    Spacer(modifier = modifier.width(12.dp))
}