package com.olesmalysh.habitstracker.core.presentation.settings_screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.compose.rememberNavController
import com.olesmalysh.habitstracker.app.LocalNavController
import com.olesmalysh.habitstracker.core.presentation.theme.AppTheme
import com.olesmalysh.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.olesmalysh.habitstracker.core.presentation.theme.screenBackgroundDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateUsScreen(
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    var rating by remember { mutableIntStateOf(0) }

    // Logic: 4-5 stars is "High", 1-3 is "Low", 0 is "None"
    val isHighRating by remember { derivedStateOf { rating >= 4 } }
    val hasRating by remember { derivedStateOf { rating > 0 } }

    // Dynamic content based on specific rating
    val (emoji, title, subtitle) = when (rating) {
        0 -> Triple("\uD83E\uDD7A", "Rate our app", "Tap a star to give your feedback.")
        1 -> Triple("😫", "Oh no!", "Please let us know what went wrong.")
        2 -> Triple("😔", "We can do better", "Your feedback helps us improve.")
        3 -> Triple("😐", "Thanks for rating", "We appreciate your honest feedback.")
        4 -> Triple("😊", "We like you too!", "Thanks for your support!")
        5 -> Triple("🥳", "You are awesome!", "The best we can get :)")
        else -> Triple("\uD83E\uDD7A", "Rate Us", "")
    }

    Scaffold(
        modifier = modifier.padding(vertical = 8.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Rate Us",
                            color = Color.White,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 20.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent, contentColor = Color.White
                        ),
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = "Go Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = screenBackgroundDark),
                actions = { IconButton(onClick = { }) { } }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(screenBackgroundDark)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // 1. Animated Emoji
            Text(
                text = emoji,
                fontSize = 80.sp,
                modifier = Modifier.animateContentSize()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Dynamic Title
            Text(
                text = title,
                color = Color.White,
                fontFamily = PoppinsFontFamily,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 3. Dynamic Subtitle
            Text(
                text = subtitle,
                color = Color(0xFFB8B8B8),
                fontFamily = PoppinsFontFamily,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Stars
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    val isSelected = i <= rating
                    val icon = if (isSelected) Icons.Filled.Star else Icons.Outlined.StarBorder
                    // Color logic: Gold if selected, Grey if not.
                    // Optional: Make 1-2 stars red/orange if you want, but Gold is standard UX.
                    val tint = if (isSelected) Color(0xFFFFC107) else Color(0xFF4A4A4A)

                    Icon(
                        imageVector = icon,
                        contentDescription = "Star $i",
                        tint = tint,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                rating = i
                            }
                    )
                    if (i < 5) Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Little hint under the 5th star
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                // Show hint only if not rated yet or rated 5
                if (rating == 0 || rating == 5) {
                    Text(
                        text = "The best we can get :)",
                        color = Color(0xFF8C8C8C),
                        fontFamily = PoppinsFontFamily,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 5. Button
            Button(
                onClick = {
                    if (isHighRating) {
                        openGooglePlay(context)
                    } else {
                        // Handle negative feedback (e.g. open email support or just close)
                        Toast.makeText(context, "Thanks for your feedback!", Toast.LENGTH_SHORT).show()
                        navController.navigateUp()
                    }
                },
                enabled = hasRating, // ENABLED ONLY IF RATED
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .animateContentSize(),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4263EB),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF2A2A2A), // Dark grey when disabled
                    disabledContentColor = Color(0xFF8C8C8C)
                )
            ) {
                Text(
                    text = "RATE ON GOOGLE PLAY",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 16.sp,
                    maxLines = 1
                )
            }
        }
    }
}

private fun openGooglePlay(context: Context) {
    val packageName = context.packageName
    try {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri())
        )
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW,
                "https://play.google.com/store/apps/details?id=$packageName".toUri())
        )
    }
}

@Preview
@Composable
private fun PreviewRateUs() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            RateUsScreen()
        }
    }
}