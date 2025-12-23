package com.example.habitstracker.profile.presentation.component

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AccountSettingsBottomSheet(
    sheetState: SheetState,
    buttons: List<AccountButton>,
    onCloudDataClear: () -> Unit,
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
                    modifier = Modifier.align(Alignment.TopStart),
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
                    text = stringResource(R.string.account_management),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = PoppinsFontFamily
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            AccountManagementButtons(
                buttonsList = buttons,
                onCloudDataClear = {
                    onCloudDataClear()
                    closeBottomSheet()
                }
            )
        }
    }
    Spacer(Modifier.height(10.dp))
}

data class AccountButton(
    val name: UiText,
    val image: ImageVector,
    val color: Color = Color.White,
    val buttonType: SettingsButtonType = SettingsButtonType.OTHER,
    val onClick: () -> Unit
)

enum class SettingsButtonType {
    CLEAR_DATA,
    LOGOUT,
    UPLOAD_DATA,
    OTHER
}