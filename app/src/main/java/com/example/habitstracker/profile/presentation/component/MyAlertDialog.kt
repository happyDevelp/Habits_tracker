package com.example.habitstracker.profile.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark

@Composable
fun MyAlertDialog(
    title: String,
    message: String = "Lorem ipsum dolor sit amet",
    annotatedMessage: AnnotatedString? = null,
    onConfirm: () -> Unit, onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = title,
                fontFamily = PoppinsFontFamily
            )
        },
        text = {
            if (annotatedMessage == null) {
                Text(
                    text = message,
                    fontFamily = PoppinsFontFamily
                )
            }
            else {
                Column {
                    Text(
                        text = annotatedMessage,
                        fontSize = 14.sp,
                        color = Color.White // Highlighting a username
                    )

                }
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(R.string.delete),
                    color = Color(0xFFFF1000),
                    fontFamily = PoppinsFontFamily
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = Color.White,
                    fontFamily = PoppinsFontFamily
                )
            }
        },
        containerColor = containerBackgroundDark
    )
}