package com.example.habitstracker.me.presentation.friends.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.habitstracker.R

@Composable
fun FriendListItem(
    name: String,
    avatarUrl: String?,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val cardColor = Color(0xFF23263A)
    val nameColor = Color(0xFFECECEC)

    // Gradient for the avatar frame
    val avatarBorderBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF4E5BFF),
            Color(0xFF8E44AD)
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- avatar ---
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(54.dp)
                    .border(1.2.dp, avatarBorderBrush, CircleShape) // Gradient frame
                    .padding(4.dp) // Indentation between the frame and the photo
                    .clip(CircleShape)
                    .background(Color.Gray) // The placeholder is still loading
            ) {
                AsyncImage(
                    model = avatarUrl ?: R.drawable.avataaar,
                    contentDescription = "Friend Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // --- text ---
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    color = nameColor,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // --- delete button ---
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.08f), // Translucent background
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Remove friend",
                    tint = Color(0xFFEF5350), // М'який червоний колір для дії видалення
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(Modifier.fillMaxSize()) {
        FriendListItem(
            name = "Matteus Müller",
            avatarUrl = null,
            onClick = {},
            onDeleteClick = {}
        )
    }
}