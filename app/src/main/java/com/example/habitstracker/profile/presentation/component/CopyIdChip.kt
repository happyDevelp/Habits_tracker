package com.example.habitstracker.profile.presentation.component

import android.content.ClipData
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CopyIdChip(
    modifier: Modifier = Modifier,
    profileCode: String,
) {
    val coroutineScope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        label = "chipPressScale"
    )

    var showTooltip by remember { mutableStateOf(false) }

    // Auto-hiding tooltip
    LaunchedEffect(showTooltip) {
        if (showTooltip) {
            delay(1500)   // 1.2 сек
            showTooltip = false
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        // Tooltip "Copied"
        if (showTooltip) {
            Box(
                modifier = Modifier
                    .offset(y = (-26).dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF1E1F26))
                    .padding(horizontal = 6.dp, vertical = 0.dp)
            ) {
                Text(
                    text = "Copied",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontFamily = PoppinsFontFamily
                )
            }
        }

        // copy button
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .height(24.dp)
                .clip(RoundedCornerShape(400.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF5865F2),
                            Color(0xFF2C3CE5)
                        )
                    )
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    val clipData = ClipData.newPlainText(
                        "my friend id", profileCode
                    )
                    coroutineScope.launch {
                        clipboard.setClipEntry(clipData.toClipEntry())
                    }
                    showTooltip = true
                }
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID: $profileCode",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PoppinsFontFamily
                )

                Spacer(modifier = Modifier.width(6.dp))

                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    contentDescription = "Copy ID",
                    tint = Color.White,
                    modifier = Modifier.size(10.dp)
                )
            }
        }
    }
}
