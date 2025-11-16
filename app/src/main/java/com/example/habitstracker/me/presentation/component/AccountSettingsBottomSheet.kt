package com.example.habitstracker.me.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AccountSettingsBottomSheet(
    sheetState: SheetState,
    modifier: Modifier,
    onSignOutClick: () -> Unit,
    onAccountDeleteClick: () -> Unit,
    closeBottomSheet: () -> Unit,

    ) {
    ModalBottomSheet(
        onDismissRequest = closeBottomSheet,
        sheetState = sheetState,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    modifier = modifier.align(Alignment.TopStart),
                    onClick = closeBottomSheet
                ) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Account Management",
                        tint = Color.White
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Account Management",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = PoppinsFontFamily
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            AccountManagementButtons(
                onLogoutClick = {
                    onSignOutClick()
                    closeBottomSheet()
                },
                onDeleteAccountClick = {
                    onAccountDeleteClick()
                    closeBottomSheet()
                }
            )
        }
    }
    Spacer(Modifier.height(10.dp))
}