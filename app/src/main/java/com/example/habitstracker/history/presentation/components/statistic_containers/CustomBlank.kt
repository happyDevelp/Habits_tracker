package com.example.habitstracker.history.presentation.components.statistic_containers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily

@Composable
fun CustomBlank(
    modifier: Modifier = Modifier,
    width: Dp = 150.dp,
    gradientColor: Brush,
    topText: String,
    middleText: String,
    bottomText: String,
) {
    Card(
        modifier = modifier.size(width = width, height = 160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientColor)
        ) {
            Column(
                modifier = modifier.padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = topText,
                    fontSize = 14.sp,
                    lineHeight = 16.sp, // Makes text more compact vertically
                    fontWeight = FontWeight.Medium,
                    fontFamily = PoppinsFontFamily,
                    color = Color.White,
                    maxLines = 3,
                    /*overflow = TextOverflow.Ellipsis,*/
                    modifier = Modifier.weight(1f)
                )
                MyText(text = middleText, textSize = 50.sp)
                Text(
                    text = bottomText,
                    fontSize = 11.sp,
                    color = Color.White.copy(0.85f),
                )
            }
        }
    }
    Spacer(modifier = modifier.width(12.dp))
}