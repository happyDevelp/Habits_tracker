package com.example.habitstracker.core.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily

@Preview(showSystemUi = true)
@Composable
fun MyText(
    modifier: Modifier = Modifier,
    text: String = "Hello World",
    color: Color = Color.White,
    fontFamily: FontFamily = PoppinsFontFamily,
    textSize: TextUnit = 20.sp
) {
    Text(
        modifier = Modifier,
        text = text,
        color = color,
        fontFamily = fontFamily,
        fontSize = textSize,
    )
}