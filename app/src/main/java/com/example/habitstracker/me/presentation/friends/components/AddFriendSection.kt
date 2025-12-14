package com.example.habitstracker.me.presentation.friends.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.me.presentation.component.FriendCodeVisualTransformation

@Composable
fun AddFriendSection(
    onAddFriendClick: (String) -> Unit,
    onShareLinkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var typedText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val interactionSourceAddButton = remember { MutableInteractionSource() }
        val isPressedAddButton by interactionSourceAddButton.collectIsPressedAsState()
        val scaleAddButton by animateFloatAsState(
            targetValue = if (isPressedAddButton) 0.96f else 1f,
            label = "scaleButton"
        )
        Row(
            modifier = Modifier
                .weight(1.5f)
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1E1F22)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val onSend = {
                // 1. Hide the keyboard
                focusManager.clearFocus()

                // 2. insert a hyphen
                val codeToSend = if (typedText.length == 8) {
                    "${typedText.take(4)}-${typedText.takeLast(4)}"
                } else {
                    typedText
                }

                // 3. send to vm
                onAddFriendClick(codeToSend)
                Log.d("MeScreen", "MeScreen: $codeToSend")
            }
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scaleAddButton
                        scaleY = scaleAddButton
                    }
                    .fillMaxHeight()
                    .background(Color(0xD75865F2))
                    .clickable(
                        interactionSource = interactionSourceAddButton,
                        indication = null
                    ) { onSend() }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ADD",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            BasicTextField(
                value = typedText,
                onValueChange = { newInput ->
                    // only clean and limit the length
                    val cleanString =
                        newInput.filter { it.isLetterOrDigit() }.uppercase()

                    if (cleanString.length <= 8) {
                        typedText = cleanString
                    }
                },
                visualTransformation = FriendCodeVisualTransformation(),
                // tell the keyboard to write in capital letters
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Done // Change Enter to the "Done" button
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onSend() }
                ),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 13.sp
                ),
                decorationBox = { innerTextField ->
                    if (typedText.isEmpty()) {
                        Text(
                            text = "Enter friend's ID",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, top = 8.dp, bottom = 8.dp),
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "or",
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.width(8.dp))

        val interSourceShareLink = remember { MutableInteractionSource() }
        val isPressedShareLink by interSourceShareLink.collectIsPressedAsState()
        val scaleSharedLink by animateFloatAsState(
            targetValue = if (isPressedShareLink) 0.96f else 1f,
            label = "scaleSharedLinkButton"
        )
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scaleSharedLink
                    scaleY = scaleSharedLink
                }
                .clickable(
                    interactionSource = interSourceShareLink,
                    indication = null
                ) { onShareLinkClick() }
                .height(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF5865F2),
                            Color(0xFF4752C4)
                        )
                    )
                )
                .padding(horizontal = 16.dp)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Share link",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
