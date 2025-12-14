package com.example.habitstracker.me.presentation.friends.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily

@Composable
fun FriendsEmptyState() {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.PeopleOutline,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.6f),
            modifier = Modifier.size(64.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "No friends yet",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 16.sp,
            fontFamily = PoppinsFontFamily
        )
    }
}