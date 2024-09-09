package com.example.habitstracker.ui.screens.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.ui.screens.history.scaffold.TopBarHistoryScreen
import com.example.habitstracker.ui.theme.AppTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showSystemUi = false)
private fun HistoryScreenPreview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            HistoryScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {

    val navController = LocalNavController.current

    Scaffold(
        topBar = { TopBarHistoryScreen() }
    ) { paddingValues ->

        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(text = "HistoryScreen", fontSize = 24.sp)
        }
    }
}