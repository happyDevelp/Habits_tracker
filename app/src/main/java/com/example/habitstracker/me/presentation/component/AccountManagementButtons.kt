package com.example.habitstracker.me.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark

@Composable
fun AccountManagementButtons(
    buttonsList: List<AccountButton>,
    onCloudDataClear: () -> Unit
) {
    val context = LocalContext.current
    val iconBackgroundColor = Color(0xFF606060)
    var showDeleteDialog by remember { mutableStateOf(false) }

    buttonsList.forEach { button ->
        Row(
            modifier = Modifier
                .clickable {
                    when (button.name) {
                        UiText.StringResources(R.string.clear_cloud_data).asString(context)
                            -> showDeleteDialog = true

                        else -> button.onClick()
                    }
                }
                .fillMaxWidth(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(color = containerBackgroundDark)
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = iconBackgroundColor),
                //.border(2.dp, Color.Gray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = button.image,
                    contentDescription = null,
                    tint = button.color
                )
            }
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = button.name,
                fontSize = 17.sp,
                color = button.color,
                fontFamily = PoppinsFontFamily
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

    }

    if (showDeleteDialog) {
        DeleteAccountDialog(
            onConfirm = {
                showDeleteDialog = false
                onCloudDataClear()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun DeleteAccountDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Confirm Clear Data From Cloud",
                fontFamily = PoppinsFontFamily
            )
        },
        text = {
            Text(
                text = "Are you sure you want to permanently delete all your cloud-stored habit data?" +
                        "\n\nThis action cannot be undone.",
                fontFamily = PoppinsFontFamily
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = "Delete",
                    color = Color(0xFFFF1000),
                    fontFamily = PoppinsFontFamily
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = Color.White,
                    fontFamily = PoppinsFontFamily
                )
            }
        },
        containerColor = containerBackgroundDark
    )
}


@Preview
@Composable
private fun Preview() {
    Column {
        AccountManagementButtons(
            listOf(
                AccountButton(
                    "Upload Data From Cloud", Icons.Outlined.CloudUpload, onClick = {}
                ),
                AccountButton(
                    "Log Out", Icons.Outlined.Logout, onClick = {}
                ),
            ),
            onCloudDataClear = {}
        )
    }
}