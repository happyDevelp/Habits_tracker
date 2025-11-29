package com.example.habitstracker.me.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.core.presentation.utils.gradientColor
import com.example.habitstracker.me.presentation.component.FrDetailTopBar

@Composable
fun FriendDetailRoot() {
    // TODO: transfer the data from ViewModel

    FriendDetailScreen()
}

@Composable
fun FriendDetailScreen(
    modifier: Modifier = Modifier,
    name: String = "Sandra",
    friendSince: String = "2025",
    currentStreak: Int = 7,
    bestStreak: Int = 10,
    totalCompleted: Int = 48,
    perfectDays: Int = 15,
    perfectDaysThisWeek: Int = 3,
    consistency: Int = 86,
) {
    val background = Color(0xFF0A0C13)   // загальний фон
    val surfaceDark = Color(0xFF11141C)   // фон великих карток
    val surfaceDarker = Color(0xFF0F1228)

    Scaffold(
        containerColor = screenBackgroundDark,
        topBar = { FrDetailTopBar() }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Головна картка з усім контентом
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background, RoundedCornerShape(28.dp))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                // 1) Хедер з аватаркою + ім’ям
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    listOf(Color(0xFF4E5BFF), Color(0xFF202540))
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // Плейсхолдер – буква імені
                        Text(
                            text = name.firstOrNull()?.uppercase() ?: "?",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = "Friend since $friendSince",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF9FA4C0)
                            )
                        )
                    }
                }

                // 2) Статистика
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Верхня широка плашка: Total completed habits
                    StatCardWide()

                    // Дві нижні плашки в ряд
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCardSmall(
                            modifier = Modifier.weight(1f),
                            title = "Best streak",
                            primaryValue = bestStreak.toString(),
                            secondaryLabel = "Current: $currentStreak",
                            gradient = Brush.verticalGradient(
                                listOf(Color(0xFF1D5DFF), Color(0xFF132349))
                            ),
                            background = surfaceDark
                        )

                        StatCardSmall(
                            modifier = Modifier.weight(1f),
                            title = "Perfect days",
                            primaryValue = perfectDays.toString(),
                            secondaryLabel = "This week: $perfectDaysThisWeek",
                            gradient = Brush.verticalGradient(
                                listOf(Color(0xFF1EC47A), Color(0xFF17342A))
                            ),
                            background = surfaceDark
                        )
                    }
                }

                // 3) Consistency
                ConsistencyCard(
                    modifier = Modifier.fillMaxWidth(),
                    value = consistency,
                    background = surfaceDark
                )
            }
        }
    }
}

@Composable
private fun StatCardWide(
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
            .padding(2.dp) // легкий бордер/світіння
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp) // фіксована висота, щоб було як плитка
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

@Composable
private fun ConsistencyCard(
    modifier: Modifier = Modifier,
    value: Int,
    background: Color,
) {
    Box(
        modifier = modifier
            .background(background, RoundedCornerShape(24.dp))
            .padding(vertical = 20.dp, horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Напівколо
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(2f),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 18.dp.toPx()
                    val diameter = size.minDimension
                    val topLeft = Offset(
                        (size.width - diameter) / 2f,
                        size.height - diameter
                    )
                    val arcSize = Size(diameter, diameter)

                    // Фон дуги
                    drawArc(
                        color = Color(0xFF2C3145),
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(strokeWidth, cap = StrokeCap.Round)
                    )

                    // Заповнена частина
                    val sweep = 180f * (value / 100f)
                    drawArc(
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFF23D2C3), Color(0xFF1A89FF))
                        ),
                        startAngle = 180f,
                        sweepAngle = sweep,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(strokeWidth, cap = StrokeCap.Round)
                    )
                }

                Text(
                    text = "${value}%",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Text(
                text = "Consistency",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = "Keep up the good work!",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF9FA4C0)
                )
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        FriendDetailScreen()
    }
}