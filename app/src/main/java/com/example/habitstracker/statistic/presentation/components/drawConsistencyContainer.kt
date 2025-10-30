package com.example.habitstracker.statistic.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark

@Composable
fun DrawConsistencyContainer(consistency: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerBackgroundDark,
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp, bottom = 24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Consistency"/*"Monthly Consistency"*/,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = PoppinsFontFamily
                )
                Spacer(Modifier.height(32.dp))

                val strokeWidth = 14.dp
                val diameter = 220.dp

                Canvas(
                    modifier = Modifier
                        .width(diameter)
                        .height(diameter / 2)
                ) {
                    val stroke = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                    val topOffset = 0f/*-size.width / 2f*/
                    val stretchingDegree = 20
                    val angle = 180f
                    // grayBase
                    drawArc(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        startAngle = angle,
                        sweepAngle = angle,
                        useCenter = false,
                        topLeft = Offset(-stretchingDegree / 2f, topOffset),
                        size = Size(size.width + stretchingDegree, size.width),
                        style = stroke
                    )

                    val consistencyArc = angle * consistency / 100
                    // The yellow filled part
                    drawArc(
                        color = Color(0xFFFFC107),
                        startAngle = angle,
                        sweepAngle = consistencyArc,
                        useCenter = false,
                        topLeft = Offset(-stretchingDegree / 2f, topOffset),
                        size = Size(size.width + stretchingDegree, size.width),
                        style = stroke
                    )
                }
            }
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 4.dp),
                text = "$consistency%",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily
            )
        }
        Text(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
            text = /*"Complete your habits every day to keep your bird cheerful!"*/
                "Perform your habits daily to increase your consistency percentage.",
            fontSize = 12.sp,
            color = Color.White.copy(0.88f),
            fontFamily = PoppinsFontFamily,
            textAlign = TextAlign.Center
        )

        // test animation #TODO
        /*BirdAnimations.HappyBird(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(250.dp)
                        .offset(y = (-70).dp),
                )*/
    }
}