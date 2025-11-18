package com.example.habitstracker.me.presentation.component

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.me.presentation.sign_in.BannerStatus
import com.example.habitstracker.me.presentation.sign_in.SignInBannerStatus

data class BannerData(
    val message: String,
    val icon: ImageVector,
    val background: Color
)

@Composable
fun TopBanner(
    status: BannerStatus,
    modifier: Modifier = Modifier
) {
    val success = Color(0xFF1F6F43)
    val fail = Color(0xFF8E1E1E)
    val warning = Color(0xFF9C6B00)

    val newData = when (status) {
        SignInBannerStatus.LOGIN_SUCCESS -> BannerData(
            "Logged in successfully",
            Icons.Default.CheckCircle,
            success
        )
        SignInBannerStatus.LOGOUT_SUCCESS -> BannerData(
            "Logged out successfully",
            Icons.Default.CheckCircle,success
        )
        SignInBannerStatus.LOGIN_FAILED -> BannerData(
            "Login failed",
            Icons.Default.Error,
            fail
        )
        SignInBannerStatus.ACCOUNT_DELETED -> BannerData(
            "Account deleted",
            Icons.Default.CheckCircle,success
        )
        SignInBannerStatus.DELETE_FAILED -> BannerData(
            "Delete failed",
            Icons.Default.Error,
            fail
        )
        SignInBannerStatus.NO_INTERNET -> BannerData(
            "No internet connection",
            Icons.Default.WifiOff,
            warning
        )

        SignInBannerStatus.NONE -> null
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
        modifier = modifier
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