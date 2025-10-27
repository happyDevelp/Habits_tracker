package com.example.habitstracker.statistic.presentation.profile.components.scaffold

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfileScreen(modifier: Modifier = Modifier) {
    TopAppBar(
        title = { MyText(text = stringResource(R.string.statistics), textSize = 26.sp) },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screenBackgroundDark
        )
    )
}