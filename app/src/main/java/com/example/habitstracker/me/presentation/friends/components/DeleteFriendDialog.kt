package com.example.habitstracker.me.presentation.friends.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.habitstracker.me.domain.model.FriendEntry
import com.example.habitstracker.me.presentation.component.MyAlertDialog

@Composable
fun DeleteFriendDialog(
    friend: FriendEntry,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    // 1. Create a "smart" string
    val styledMessage = remember(friend) {
        buildAnnotatedString {
            // First part (grayed by default from dialog)
            append("Are you sure you want to remove ")
            // Part with the name (White and Bold)
            withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                append(friend.friendDisplayName)
            }
            append(" from friends?") // End of sentence
        }
    }

    MyAlertDialog(
        title = "Delete friend",
        annotatedMessage = styledMessage,
        onConfirm = onConfirm,
        onDismiss = onDismiss
    )
}