package com.example.habitstracker.core.presentation.settingsScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current

    Scaffold(
        modifier = modifier.padding(vertical = 8.dp),

        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Account",
                            color = Color.White,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 20.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },

                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = "Go Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = screenBackgroundDark
                ),
                actions = {
                    IconButton(onClick = { }) { }
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(screenBackgroundDark),
        ) {
            Text(
                text = "Account",
                color = Color.White,
                fontFamily = PoppinsFontFamily,
                fontSize = 20.sp
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            AccountScreen()
        }
    }
}