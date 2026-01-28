package com.olesmalysh.habitstracker.core.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.olesmalysh.habitstracker.core.presentation.utils.TestTags
import com.olesmalysh.habitstracker.habit.domain.ShownHabit

@Preview()
@Composable
fun CustomCheckbox(
    modifier: Modifier = Modifier,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    shownHabit: ShownHabit = ShownHabit(),
    onClick: () -> Unit = { },
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .clip(CircleShape) // IMPORTANT: clip before bounceClickable
            .bounceClickable { onClick() }
            .testTag(TestTags.CUSTOM_CHECK_BOX + "_" + shownHabit.name),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = modifier
                .size(24.dp)
                //.scale(checkboxIconSize)
                /*.bounceClickable(
                    onAnimationFinished = {
                        onClick.invoke()
                    },
                )*/
                .testTag(TestTags.CUSTOM_CHECK_BOX + "_" + shownHabit.name),

            colors = CardDefaults.cardColors(
                containerColor = if (!shownHabit.isSelected) Color.Transparent
                else Color(0xFF3dbe57)
            ),

            border = if (!shownHabit.isSelected)
                BorderStroke(2.dp, Color.LightGray.copy(alpha = 0.70f))
            else
                BorderStroke(0.dp, Color.Transparent),


        ) {
            Icon(
                modifier = Modifier.padding(0.dp),
                imageVector = Icons.Default.Check,
                contentDescription = "Task is done button",
                tint = if (shownHabit.isSelected) Color.White
                else Color.Transparent
            )
        }
    }
}

fun Modifier.bounceClickable(
    enabled: Boolean = true,
    pressedScale: Float = 0.88f,
    downDurationMs: Int = 70,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    onAnimationFinished: () -> Unit
): Modifier = composed {

    val source = interactionSource ?: remember { MutableInteractionSource() }
    val isPressed = source.collectIsPressedAsState()

    val callback = rememberUpdatedState(onAnimationFinished)

    val scale = remember { Animatable(1f) }
    val rippleIndication = indication ?: ripple(bounded = true)

    // This flag triggers "tap bounce" even if press state was too short to notice.
    val tapBounce = remember { androidx.compose.runtime.mutableStateOf(0) }

    // 1) Handle press/hold animation via LaunchedEffect (no launch inside composition)
    LaunchedEffect(isPressed.value) {
        if (isPressed.value) {
            // Finger is down
            scale.animateTo(
                targetValue = pressedScale,
                animationSpec = tween(durationMillis = downDurationMs)
            )
        } else {
            // Finger released -> return to normal (if no tap bounce is running)
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(dampingRatio = 0.65f, stiffness = Spring.StiffnessMedium)
            )
        }
    }

    // 2) Handle fast-tap bounce (force visible animation)
    LaunchedEffect(tapBounce.value) {
        if (tapBounce.value == 0) return@LaunchedEffect

        // Force a short down-up even for very fast clicks
        scale.snapTo(1f)
        scale.animateTo(pressedScale, tween(durationMillis = downDurationMs))
        scale.animateTo(
            1f,
            spring(dampingRatio = 0.65f, stiffness = Spring.StiffnessMedium)
        )

        callback.value.invoke()
    }

    this
        .graphicsLayer {
            scaleX = scale.value
            scaleY = scale.value
        }
        .indication(source, rippleIndication)
        .clickable(
            enabled = enabled,
            interactionSource = source,
            indication = null
        ) {
            // Trigger tap bounce. Incrementing value retriggers LaunchedEffect.
            tapBounce.value += 1
        }
}