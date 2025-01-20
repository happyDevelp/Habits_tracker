package com.example.habitstracker.profile.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.profile.presentation.profile.component.ButtonItem
import com.example.habitstracker.profile.presentation.profile.component.CustomContainer
import com.example.habitstracker.profile.presentation.profile.component.SettingsButtonItem
import com.example.habitstracker.profile.presentation.profile.component.scaffold.TopBarProfileScreen
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.core.presentation.theme.orangeColor
import com.example.habitstracker.core.presentation.theme.redColor
import com.example.habitstracker.core.presentation.theme.screensBackgroundDark
import com.example.habitstracker.core.presentation.utils.APP_VERSION

@Composable
@Preview(showSystemUi = false)
private fun ProfileScreenPreview() {
    AppTheme(darkTheme = true) { ProfileScreen() }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopBarProfileScreen() },
        containerColor = screensBackgroundDark
    ) { paddingValues ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            CustomContainer(
                modifier = modifier
                    .padding(top = 20.dp, start = 12.dp, end = 12.dp)
                    .padding(paddingValues)
                    .height(180.dp),
            ) {
                val topButtonList = getTopButtonsList()
                DrawTopButtons(topButtonList)
            }

            CustomContainer(
                modifier = modifier
                    .padding(top = 30.dp, start = 12.dp, end = 12.dp)
                    .height(180.dp),
            ) {
                val bottomButtonList = getBottomButtonsList()
                DrawTopButtons(bottomButtonList)
            }

            MyText(
                modifier = modifier.padding(top = 12.dp),
                text = "Version $APP_VERSION",
                textSize = 15.sp,
                color = Color.White.copy(0.6f)
            )

        }
    }
}

@Composable
private fun DrawTopButtons(buttonList: List<ButtonItem>) {
    buttonList.forEach { button ->
        SettingsButtonItem(
            icon = button.icon,
            iconBackground = button.iconBackground,
            text = button.text
        )
    }
}

@Composable
fun getTopButtonsList() =
    listOf(
        ButtonItem(
            icon = Icons.Default.Notifications,
            iconBackground = blueColor,
            text = stringResource(id = R.string.notification)
        ),
        ButtonItem(
            icon = Icons.Default.Settings,
            iconBackground = orangeColor,
            text = stringResource(R.string.general_settings)
        ),
        ButtonItem(
            icon = Icons.Default.Language,
            iconBackground = redColor,
            text = stringResource(R.string.language_options)
        ),
    )

@Composable
fun getBottomButtonsList() =
    listOf(
        ButtonItem(
            icon = Icons.Default.Share,
            iconBackground = blueColor,
            text = stringResource(R.string.share_with_friends)
        ),
        ButtonItem(
            icon = Icons.Default.Star,
            iconBackground = Color(0xFF14AD8E),
            text = stringResource(R.string.rate_us)
        ),
        ButtonItem(
            icon = Icons.Default.Feedback,
            iconBackground = Color(0xFF4788D6),
            text = stringResource(R.string.feedback)
        ),
    )