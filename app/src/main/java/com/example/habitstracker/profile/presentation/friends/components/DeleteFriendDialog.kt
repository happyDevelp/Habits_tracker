package com.example.habitstracker.profile.presentation.friends.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.habitstracker.R
import com.example.habitstracker.profile.domain.model.FriendEntry
import com.example.habitstracker.profile.presentation.component.MyAlertDialog

@Composable
fun DeleteFriendDialog(
    friend: FriendEntry,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val template = stringResource(R.string.delete_friend_confirmation_template)
    // 1. Create a "smart" string
    val styledMessage = remember(friend.friendDisplayName, template) {
        buildAnnotatedString {
            // Breaking the line by placeholder
            // %1$s is the standard format for string resource, so we'll weave over it
            val parts = template.split("%1\$s")

            // 1. First part of the sentence (to the name)
            if (parts.isNotEmpty()) {
                append(parts[0])
            }

            // 2.Name Itself (Stylized)
            withStyle(
                style = SpanStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(friend.friendDisplayName)
            }

            // 3. The second part of the sentence (after the name), if there is one
            if (parts.size > 1) {
                append(parts[1])
            }


        }
    }

    MyAlertDialog(
        title = stringResource(R.string.delete_friend),
        annotatedMessage = styledMessage,
        onConfirm = onConfirm,
        onDismiss = onDismiss
    )
}