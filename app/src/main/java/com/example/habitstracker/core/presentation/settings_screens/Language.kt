package com.example.habitstracker.core.presentation.settings_screens

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import java.util.Locale

enum class AppLanguage(val title: String, val code: String) {
    EN("English", "en"),
    DE("Deutsch", "de"),
    UA("Українська", "uk")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    modifier: Modifier = Modifier,
    onSelect: (AppLanguage) -> Unit = {}
) {
    val navController = LocalNavController.current

    val currentLanguage = remember { getCurrentAppLanguage() }
    Scaffold(
        modifier = modifier.padding(vertical = 8.dp),
        containerColor = screenBackgroundDark,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.language),
                        color = Color.White,
                        fontFamily = PoppinsFontFamily,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = "Go Back",
                            tint = Color.White
                        )
                    }
                },
                actions = { Spacer(Modifier.size(48.dp)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = screenBackgroundDark)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 18.dp)
                .padding(top = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(AppLanguage.entries) { item ->
                LanguageOptionCard(
                    text = item.title,
                    selected = item == currentLanguage,
                    onClick = { changeLanguage(item.code) }
                )
            }
        }
    }
}

@Composable
private fun LanguageOptionCard(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg by animateColorAsState(
        targetValue = if (selected) Color(0xFF4C5FA0) else Color(0xFF2D3145),
        label = "bg"
    )
    val stroke by animateColorAsState(
        targetValue = if (selected) Color(0xFF6E7ED6) else Color(0xFF3A3F57),
        label = "stroke"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(32.dp),
                clip = false
            )
            .clip(RoundedCornerShape(32.dp))
            .background(bg)
            .border(1.dp, stroke, RoundedCornerShape(32.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontFamily = PoppinsFontFamily,
            fontSize = 20.sp
        )

        Spacer(Modifier.weight(1f))

        if (selected) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF6F7CF0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .border(2.dp, Color.White.copy(alpha = 0.35f), CircleShape)
            )
        }
    }
}

fun changeLanguage(languageCode: String) {
    // languageCode
    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
    AppCompatDelegate.setApplicationLocales(appLocale)
}

fun getCurrentAppLanguage(): AppLanguage {
    // 1. Getting a list
    val locales = AppCompatDelegate.getApplicationLocales()

    // 2. We check if it is empty
    val languageCode = if (!locales.isEmpty) {
        locales[0]?.language
    } else {
        // If it is empty, we take the language of the system
        Locale.getDefault().language
    }

    // 3. Шwe put EN/DE in our list, if we don't find it, we return EN
    return AppLanguage.entries.find { it.code == languageCode } ?: AppLanguage.EN
}

@Preview
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            LanguageScreen()
        }
    }
}