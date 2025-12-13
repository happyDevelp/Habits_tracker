package com.example.habitstracker.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
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

val openSans = GoogleFont("Open Sans")
val raleway = GoogleFont("Raleway")
val Alfa_Slab_One = GoogleFont("Alfa Slab One")

object Fonts {
/*    val OpenSans = FontFamily(
        Font(
            googleFont = openSans,
            fontProvider = GoogleFont.Provider(
                providerAuthority = "com.google.android.gms.fonts",
                providerPackage = "com.google.android.gms",
                certificates = R.array.com_google_android_gms_fonts_certs
            ),
        )
    )*/

    val Raleway = FontFamily(
        Font(R.font.raleway_thin, FontWeight.Thin),
        Font(R.font.raleway_extra_light, FontWeight.ExtraLight),
        Font(R.font.raleway_light, FontWeight.Light),
        Font(R.font.raleway_regular, FontWeight.Normal),
        Font(R.font.raleway_medium, FontWeight.Medium),
        Font(R.font.raleway_semibold, FontWeight.SemiBold),
        Font(R.font.raleway_bold, FontWeight.Bold),
        Font(R.font.raleway_extra_bold, FontWeight.ExtraBold),
    )
}
