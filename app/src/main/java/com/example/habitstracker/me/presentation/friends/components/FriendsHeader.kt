package com.example.habitstracker.me.presentation.friends.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.me.domain.model.UserProfile
import com.example.habitstracker.me.presentation.component.CopyIdChip
import com.example.habitstracker.me.presentation.sign_in.SignInState

@Composable
fun FriendsHeader(
    signInState: SignInState,
    profileState: UserProfile
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Friends",
            color = Color.White.copy(alpha = 0.95f),
            fontSize = 21.sp,
            fontFamily = PoppinsFontFamily,
        )
        if (signInState.userData != null) {
            CopyIdChip(
                profileCode = profileState.profileCode,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
    Spacer(Modifier.height(16.dp))
}
