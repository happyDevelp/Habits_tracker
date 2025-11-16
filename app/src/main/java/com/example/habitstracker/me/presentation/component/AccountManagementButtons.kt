package com.example.habitstracker.me.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark

@Composable
fun AccountManagementButtons(onLogoutClick: () -> Unit, onDeleteAccountClick: () -> Unit) {
    val iconBackgroundColor = Color(0xFF606060)

    var showDeleteDialog by remember { mutableStateOf(false) }

    // Button 1
    Row(
        modifier = Modifier
            .clickable { onLogoutClick() }
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
                imageVector = Icons.Outlined.Logout,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "Log Out",
            fontSize = 17.sp,
            color = Color.White,
            fontFamily = PoppinsFontFamily
        )
    }
    Spacer(modifier = Modifier.height(10.dp))

    // Button 2
    Row(
        modifier = Modifier
            .clickable { /*onDeleteAccountClick()*/
                showDeleteDialog = true
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
            /*.border(2.dp, Color.Gray, RoundedCornerShape(12.dp))*/
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.DeleteForever,
                contentDescription = null,
                tint = Color(0xFFFF1000)
            )
        }
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "Delete Account",
            fontSize = 17.sp,
            color = Color(0xFFFF1000),
            fontFamily = PoppinsFontFamily
        )
    }

    if (showDeleteDialog) {
        DeleteAccountDialog(
            onConfirm = {
                showDeleteDialog = false
                onDeleteAccountClick()
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
                text = "Confirm Account Deletion",
                fontFamily = PoppinsFontFamily
            )
        },
        text = {
            Text(
                text = "Are you sure you want to permanently delete your account?\nThis action cannot be undone.",
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