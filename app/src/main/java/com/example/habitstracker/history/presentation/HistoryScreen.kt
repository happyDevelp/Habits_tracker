package com.example.habitstracker.history.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.history.presentation.components.HistoryPager
import com.example.habitstracker.history.presentation.components.scaffold.TopBarHistoryScreen
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.screensBackgroundDark

@Composable
@Preview(showSystemUi = false)
private fun HistoryScreenPreview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            HistoryScreen(changeSelectedItemState = {})
        }
    }
}


@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    changeSelectedItemState: (index: Int) -> Unit
) {
    Scaffold(
        topBar = { TopBarHistoryScreen() },
        containerColor = screensBackgroundDark
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            HistoryPager(changeSelectedItemState = changeSelectedItemState)
        }
    }
}



