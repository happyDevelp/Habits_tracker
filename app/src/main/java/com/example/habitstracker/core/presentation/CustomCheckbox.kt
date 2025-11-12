package com.example.habitstracker.core.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habitstracker.core.presentation.utils.TestTags
import com.example.habitstracker.habit.domain.ShownHabit

@Preview()
@Composable
fun CustomCheckbox(
    modifier: Modifier = Modifier,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    shownHabit: ShownHabit = ShownHabit(),
    onClick: () -> Unit = { },
) {
    Card(
        modifier = modifier
            .size(22.dp)
            //.scale(checkboxIconSize)
            .bounceClickable(
                onAnimationFinished = {
                    onClick.invoke()
                },
            )
            .testTag(TestTags.CUSTOM_CHECK_BOX + "_" + shownHabit.name),

        colors = CardDefaults.cardColors(
            containerColor = if (!shownHabit.isSelected) Color.Transparent
            else Color(0xFF3dbe57)
        ),

        border = if (!shownHabit.isSelected)
            BorderStroke(2.dp, Color.LightGray.copy(alpha = 0.70f))
        else
            BorderStroke(0.dp, Color.Transparent),

        shape = RoundedCornerShape(20.dp),
    ) {
        Icon(
            modifier = modifier.padding(2.dp),
            imageVector = Icons.Default.Check,
            contentDescription = "Task is done button",
            tint = if (shownHabit.isSelected) Color.White
            else Color.Transparent
        )
    }
}

fun Modifier.bounceClickable(
    minScale: Float = 0.7f,
    onAnimationFinished: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) = composed {
    val interactionSource = remember { MutableInteractionSource() }

    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) minScale else 1f,
        label = ""
    ) {
        if (isPressed) {
            isPressed = false
            onAnimationFinished?.invoke()
        }
    }

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = ripple()
        ) {
            isPressed = true
            onClick?.invoke()
        }
}