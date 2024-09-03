package com.example.habitstracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R

val MyFontFamily = FontFamily(
    Font(R.font.rubik_variable_font_wght, FontWeight.Normal),
)

val QuickSandFontFamily = FontFamily(
    Font(R.font.quicksand_rubik_wght, FontWeight.Normal),
)

val BoldFontFamily = FontFamily(
    Font(R.font.robotoslab_variablefont_wght),
)

val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins_bold, FontWeight.Bold)
)

val AppTypography = Typography(
/*    bodySmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp //
    )*/
)