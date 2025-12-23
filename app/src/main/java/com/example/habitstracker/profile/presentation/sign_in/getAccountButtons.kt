package com.example.habitstracker.profile.presentation.sign_in

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.profile.presentation.component.AccountButton
import com.example.habitstracker.profile.presentation.component.SettingsButtonType

fun getAccountButtons(
    closeButtonsSheet: () -> Unit,
    syncFromCloud: () -> Unit, onSignOutClick: () -> Unit
): MutableState<List<AccountButton>> =
    mutableStateOf(
        listOf(
            AccountButton(
                UiText.StringResources(R.string.upload_data_from_cloud),
                Icons.Outlined.CloudUpload,
                onClick = {
                    closeButtonsSheet()
                    syncFromCloud()
                },
                buttonType = SettingsButtonType.UPLOAD_DATA
            ),
            AccountButton(
                UiText.StringResources(R.string.log_out),
                Icons.Outlined.Logout,
                onClick = {
                    closeButtonsSheet()
                    onSignOutClick()
                },
                buttonType = SettingsButtonType.LOGOUT
            ),
            AccountButton(
                UiText.StringResources(R.string.clear_cloud_data),
                Icons.Outlined.DeleteForever,
                color = Color(0xFFFF1000),
                onClick = { },
                buttonType = SettingsButtonType.CLEAR_DATA
            )
        )
    )
