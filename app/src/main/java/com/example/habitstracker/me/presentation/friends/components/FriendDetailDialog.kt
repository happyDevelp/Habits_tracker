package com.example.habitstracker.me.presentation.friends.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.utils.gradientColor
import com.example.habitstracker.me.domain.model.FriendEntry
import com.example.habitstracker.me.domain.model.UserStats
import com.example.habitstracker.statistic.presentation.components.DrawConsistencyContainer
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun FriendStatsDialog(
    modifier: Modifier = Modifier,
    friend: FriendEntry = FriendEntry(),
    stats: UserStats = UserStats(),
    closeFriendStats: () -> Unit = { }
) {
    val background = Color(0xFF0E101A)   // General background
    val surfaceDark = Color(0xFF11141C)   // Cards Background

    Dialog(
        onDismissRequest = { closeFriendStats() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .padding(horizontal = 18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF191933)
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                // Main card with all content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(background, RoundedCornerShape(28.dp))
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    // 1) Header with avatar + name
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FriendAvatarWithGlow(avatarUrl = friend.friendAvatarUrl)

                        Spacer(modifier = Modifier.width(16.dp))


                        Column {
                            Text(
                                text = friend.friendDisplayName,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            val instant = Instant.ofEpochMilli(friend.friendSince)
                            val date = instant.atZone(ZoneId.systemDefault()).toLocalDate()
                            val formatter =
                                DateTimeFormatter.ofPattern(
                                    "MMM yyyy", Locale.getDefault()
                                )

                            val friendSince = date.format(formatter).lowercase()

                            Text(
                                text = "friend since: $friendSince",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF9FA4C0)
                                )
                            )
                        }

                        // flexible spacer between text and button. It works like a spring.
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            modifier = Modifier.align(Alignment.Top),
                            onClick = { closeFriendStats() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                tint = Color(0xFF8BB0FF),
                                contentDescription = "close friend stats"
                            )
                        }
                    }

                    // 2) STATS
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Top wide die: Total completed habits
                        StatCardWide(value = stats.totalCompletedHabits.toString())

                        // Two bottom dies in a row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatCardSmall(
                                modifier = Modifier.weight(1f),
                                title = "Best streak",
                                primaryValue = stats.bestStreak.toString(),
                                secondaryLabel = "Current: ${stats.currentStreak}",
                                gradient = Brush.verticalGradient(
                                    listOf(Color(0xFF1D5DFF), Color(0xFF132349))
                                ),
                                background = surfaceDark
                            )

                            StatCardSmall(
                                modifier = Modifier.weight(1f),
                                title = "Perfect days",
                                primaryValue = stats.perfectDaysTotal.toString(),
                                secondaryLabel = "This week: ${stats.perfectDaysThisWeek}",
                                gradient = Brush.verticalGradient(
                                    listOf(Color(0xFF1EC47A), Color(0xFF17342A))
                                ),
                                background = surfaceDark
                            )
                        }
                    }

                    // 3) Consistency
                    DrawConsistencyContainer(
                        percentage = stats.consistencyPercent.toInt(),
                        backgroundColor = Color(0xFF181C28)
                    )
                }
            }
        }
    }
}

@Composable
fun StatCardWide(
    title: String = "Total completed habits",
    value: String = "48",
    gradient: Brush = gradientColor(
        Color(0xFFEE9243),
        Color(0xFFE83F95),
        radius = 850f
    ),
    background: Color = Color(0xFF11141C)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(background, RoundedCornerShape(22.dp))
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(gradient, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    ),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 32.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun StatCardSmall(
    modifier: Modifier = Modifier,
    title: String,
    primaryValue: String,
    secondaryLabel: String,
    gradient: Brush,
    background: Color,
) {
    Box(
        modifier = modifier
            .background(background, RoundedCornerShape(22.dp))
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient, RoundedCornerShape(20.dp))
                .padding(vertical = 12.dp, horizontal = 12.dp)
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    ),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = primaryValue,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = secondaryLabel,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xFFDFE2FF)
                    ),
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        Box(modifier = Modifier.fillMaxSize()) {
            FriendStatsDialog()
        }
    }
}