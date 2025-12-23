package com.example.habitstracker.profile.presentation.friends.components

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.habitstracker.R

@Composable
fun FriendAvatarWithGlow(avatarUrl: String?) {
    val context = LocalContext.current
    var glowColor by remember { mutableStateOf(Color(0xFF4E5BFF)) }

    Box(
        modifier = Modifier.size(75.dp), // set the size from outside
        contentAlignment = Alignment.Center
    ) {
        // The glow circle is larger than the avatar
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    brush = Brush.radialGradient(
                        listOf(glowColor.copy(alpha = 0.85f), Color.Transparent),
                    ),
                    shape = CircleShape
                )
        )

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(avatarUrl ?: R.drawable.avataaar)
                .allowHardware(false)
                .listener(
                    onSuccess = { _, result ->
                        val bitmap = (result.drawable as? BitmapDrawable)?.bitmap
                        if (bitmap != null) {
                            Palette.from(bitmap).generate { palette ->
                                val swatch = palette?.vibrantSwatch
                                    ?: palette?.lightVibrantSwatch
                                    ?: palette?.dominantSwatch
                                val rgb = swatch?.rgb
                                if (rgb != null) {
                                    glowColor = Color(rgb)
                                }
                            }
                        }
                    }
                )
                .build(),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFF101320), CircleShape)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    FriendAvatarWithGlow("https://lh3.googleusercontent.com/a/ACg8ocJ5zJDEH-ADogk8ipsSDpPafILXlp_cb_HTE6GwdKgoKqfnWMkj=s96-c")
}
