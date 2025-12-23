package com.example.habitstracker.profile.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.profile.presentation.friends.components.FriendsBannerStatus
import com.example.habitstracker.profile.presentation.sign_in.SignInBannerStatus
import com.example.habitstracker.profile.presentation.sync.SyncBannerStatus

data class BannerData(
    val message: String,
    val icon: ImageVector,
    val background: Color
)

@Composable
fun TopBanner(status: BannerStatus) {
    val success = Color(0xFF1F6F43)
    val fail = Color(0xFF8E1E1E)
    val warning = Color(0xFF9C6B00)

    val newData = when (status) {

        /** SIGN IN BANNER */
        SignInBannerStatus.LOGIN_SUCCESS -> BannerData(
            stringResource(R.string.logged_in_successfully),
            Icons.Default.CheckCircle,
            success
        )
        SignInBannerStatus.LOGOUT_SUCCESS -> BannerData(
            stringResource(R.string.logged_out_successfully),
            Icons.Default.CheckCircle,success
        )
        SignInBannerStatus.LOGIN_FAILED -> BannerData(
            stringResource(R.string.login_failed),
            Icons.Default.Error,
            fail
        )
        SignInBannerStatus.NO_INTERNET -> BannerData(
            stringResource(R.string.no_internet_connection),
            Icons.Default.WifiOff,
            warning
        )
        SignInBannerStatus.NONE -> null


        /** SYNC BANNER */
        SyncBannerStatus.NO_INTERNET -> BannerData(
            stringResource(R.string.no_internet_connection),
            Icons.Default.WifiOff,
            warning
        )
        SyncBannerStatus.SYNC_TO_CLOUD_SUCCESS -> BannerData(
            stringResource(R.string.sync_successful),
            Icons.Default.CheckCircle,
            success
        )
        SyncBannerStatus.SYNC_TO_CLOUD_FAIL -> BannerData(
            stringResource(R.string.sync_failed),
            Icons.Default.Error,
            fail
        )
        SyncBannerStatus.SYNC_FROM_CLOUD_SUCCESS -> BannerData(
            stringResource(R.string.download_data_from_cloud_successful),
            Icons.Default.CheckCircle,
            success
        )
        SyncBannerStatus.SYNC_FROM_CLOUD_FAIL -> BannerData(
            stringResource(R.string.download_data_from_cloud_failed),
            Icons.Default.Error,
            fail
        )
        SyncBannerStatus.CLOUD_DATA_DELETED -> BannerData(
            stringResource(R.string.cloud_data_successful_deleted),
            Icons.Default.CheckCircle,
            success
        )
        SyncBannerStatus.CLOUD_DATA_DELETED_FAILED -> BannerData(
            stringResource(R.string.cloud_data_deletion_failed),
            Icons.Default.Error,
            fail
        )
        SyncBannerStatus.NONE -> null

        /** FRIENDS BANNER */
        FriendsBannerStatus.REQUEST_SENT_SUCCESS -> BannerData(
            stringResource(R.string.friend_request_sent),
            Icons.Default.Send,
            success
        )
        FriendsBannerStatus.USER_NOT_FOUND -> BannerData(
            stringResource(R.string.user_with_this_id_not_found),
            Icons.Default.PersonOff,
            fail
        )
        FriendsBannerStatus.ALREADY_FRIENDS -> BannerData(
            stringResource(R.string.you_are_already_friends_with_this_user),
            Icons.Default.Group,
            Color(0xFF9C6B00)
        )
        FriendsBannerStatus.CANNOT_ADD_SELF -> BannerData(
            stringResource(R.string.you_cannot_add_yourself),
            Icons.Default.Warning,
            warning
        )
        FriendsBannerStatus.REQUEST_FAILED -> BannerData(
            stringResource(R.string.failed_to_send_request),
            Icons.Default.Error,
            fail
        )
        FriendsBannerStatus.NO_INTERNET -> BannerData(
            stringResource(R.string.no_internet_connection),
            Icons.Default.WifiOff,
            warning
        )

        FriendsBannerStatus.NONE -> null

        else -> null
    }

    // Holding the last non-null banner
    var lastNonNullData by remember { mutableStateOf<BannerData?>(null) }
    if (newData != null) {
        lastNonNullData = newData
    }

    AnimatedVisibility(
        visible = newData != null,
        enter = slideInVertically(
            initialOffsetY = { -it }
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { -it }
        ) + fadeOut(),
    ) {

        // Important: the banner does not disappear from the declaration instantly
        lastNonNullData?.let { banner ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(banner.background)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = banner.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = banner.message,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = PoppinsFontFamily
                )
            }
        }
    }
}