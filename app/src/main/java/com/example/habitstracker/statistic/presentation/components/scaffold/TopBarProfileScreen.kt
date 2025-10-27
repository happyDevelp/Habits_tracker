package com.example.habitstracker.statistic.presentation.profile.components.scaffold

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.core.presentation.utils.BirdAnimations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfileScreen(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
/*                BirdAnimations.HappyBird(modifier = Modifier.size(70.dp))
                Spacer(Modifier.width(8.dp))*/
                MyText(text = stringResource(R.string.statistics), textSize = 26.sp)
            }
        },

        actions = {
            BirdAnimations.HappyBird(
                modifier = Modifier
                    .size(65.dp)
                    .padding(bottom = 8.dp),
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screenBackgroundDark
        )
    )
}