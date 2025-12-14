package com.example.habitstracker.me.presentation.sign_in

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.habitstracker.me.presentation.component.AccountButton

fun getAccountButtons(
    closeButtonsSheet: () -> Unit,
    syncFromCloud: () -> Unit, onSignOutClick: () -> Unit
): MutableState<List<AccountButton>> =
    mutableStateOf(
        listOf(
            AccountButton(
                "Upload Data From Cloud",
                Icons.Outlined.CloudUpload,
                onClick = {
                    closeButtonsSheet()
                    syncFromCloud()
                }
            ),
            AccountButton(
                "Log Out",
                Icons.Outlined.Logout,
                onClick = {
                    closeButtonsSheet()
                    onSignOutClick()
                }
            ),
            AccountButton(
                "Clear Cloud Data",
                Icons.Outlined.DeleteForever,
                color = Color(0xFFFF1000),
                onClick = { }
            )
        )
    )
