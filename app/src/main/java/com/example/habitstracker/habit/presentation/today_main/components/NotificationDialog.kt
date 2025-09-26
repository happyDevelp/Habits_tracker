package com.example.habitstracker.habit.presentation.today_main.components

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.core.presentation.theme.BoldFontFamily
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.utils.clickWithRipple
import com.example.habitstracker.history.domain.AchievementEntity

@Composable
fun NotificationDialog(
    unlockedAchievement: UnlockedAchievement,
    onDismiss: () -> Unit,
    changeSelectedItemState: (index: Int) -> Unit
) {
    val navController = LocalNavController.current
    // this Box creates a translucent background, which darkens the main content.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.65f))
            .clickable( // add Clickable to close the dialogue by click on the background.
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // Without the effect of “waves”
                onClick = onDismiss
            ),
        contentAlignment = Alignment.Center
    ) {
        // Card with content. We add clickable so that clicks on the card do not close the dialogue
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clickable(enabled = false) {}, //Empty clickable blocks clicks
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF2196F3), Color(0xFF0D47A1)),
                            start = Offset(0f, 0f),
                            end = Offset(0f, Float.POSITIVE_INFINITY)
                        ),
                        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Congratulations!",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(150.dp)
                ) {
                    Icon(
                        painter = painterResource(id = unlockedAchievement.iconRes),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(150.dp)
                    )
                    Text(
                        text = unlockedAchievement.target.toString(),
                        fontSize = 20.sp,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = blueColor,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = unlockedAchievement.textPadding)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.new_achievement),
                    fontSize = 20.sp,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = unlockedAchievement.description,
                    fontSize = 14.sp,
                    fontFamily = PoppinsFontFamily,
                    color = Color.White.copy(0.8f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(26.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(55.dp),
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "CLOSE",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = stringResource(R.string.my_achievements),
                    fontSize = 14.sp,
                    fontFamily = BoldFontFamily,
                    fontWeight = FontWeight.Thin,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .drawBehind {
                            val underlineHeight = 1.dp.toPx()
                            val y = size.height
                            drawRect(
                                color = Color.White.copy(0.85f),
                                topLeft = Offset(4.dp.toPx(), y - underlineHeight),
                                size = Size(
                                    size.width - 8.dp.toPx(),
                                    underlineHeight - 2.7.dp.toPx()
                                )
                            )
                        }
                        .clickWithRipple(Color.White) {
                            onDismiss()
                            changeSelectedItemState(1)
                            navController.navigate(Route.History(2)) {
                                popUpTo(Route.BottomBarGraph) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(bottom = 4.dp, top = 2.dp, start = 4.dp, end = 4.dp)
                )
            }
        }
    }
    ConfettiEffect()
}

data class UnlockedAchievement(
    /*@StringRes */val iconRes: Int,
    val target: Int,
    val description: String,
    val textPadding: Dp
)