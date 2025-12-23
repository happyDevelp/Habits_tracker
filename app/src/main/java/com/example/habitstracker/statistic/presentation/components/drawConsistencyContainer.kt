package com.example.habitstracker.statistic.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark

@Composable
fun DrawConsistencyContainer(
    modifier: Modifier = Modifier,
    percentage: Int,
    // texts
    title: String? = stringResource(R.string.consistency),
    subtitle: String? = stringResource(R.string.consistency_subtitle),
    // sizes
    strokeWidth: Dp = 18.dp,
    diameter: Dp = 220.dp,
    arcAngle: Float = 180f,
    // colors
    backgroundColor: Color = containerBackgroundDark,
    baseArcColor: Color = Color.LightGray.copy(alpha = 0.3f),
    progressBrush: Brush = Brush.horizontalGradient(
        listOf(
            Color(0xFF1FA2FF),
            Color(0xFF29EAC4)
        )
    ),
    // Card Shape/Indentation
    cardShape: Shape = RoundedCornerShape(20.dp),
    cardPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
    // Text styles
    titleTextStyle: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        fontFamily = PoppinsFontFamily
    ),
    valueTextStyle: TextStyle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontFamily = PoppinsFontFamily
    ),
    subtitleTextStyle: TextStyle = TextStyle(
        fontSize = 12.sp,
        color = Color.White.copy(alpha = 0.88f),
        fontFamily = PoppinsFontFamily,
        textAlign = TextAlign.Center
    )
) {
    val clamped = percentage.coerceIn(0, 100)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(cardPadding),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = cardShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (title != null) {
                Text(
                    text = title,
                    style = titleTextStyle
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            // arc + % inside
            Box(
                modifier = Modifier
                    .width(diameter)
                    .height(diameter / 2),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .matchParentSize()
                ) {
                    val stroke = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )

                    val stretchingDegree = 20
                    val topOffset = 0f

                    // BASIC GRAY ARC
                    drawArc(
                        color = baseArcColor,
                        startAngle = arcAngle,
                        sweepAngle = arcAngle,
                        useCenter = false,
                        topLeft = Offset(-stretchingDegree / 2f, topOffset),
                        size = Size(size.width + stretchingDegree, size.width),
                        style = stroke
                    )

                    // Filled part
                    val sweep = arcAngle * (clamped / 100f)
                    drawArc(
                        brush = progressBrush,
                        startAngle = arcAngle,
                        sweepAngle = sweep,
                        useCenter = false,
                        topLeft = Offset(-stretchingDegree / 2f, topOffset),
                        size = Size(size.width + stretchingDegree, size.width),
                        style = stroke
                    )
                }

                Text(
                    modifier = Modifier.offset(y = (20).dp),
                    text = "$clamped%",
                    style = valueTextStyle
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            if (subtitle != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = subtitle,
                    style = subtitleTextStyle
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview(modifier: Modifier = Modifier) {

}