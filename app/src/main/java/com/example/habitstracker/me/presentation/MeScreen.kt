package com.example.habitstracker.me.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.Fonts
import com.example.habitstracker.core.presentation.theme.HabitColor
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.me.presentation.component.MeTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    var isUserSignedIn by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = { MeTopBar() }) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(screenBackgroundDark)
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = containerBackgroundDark),
            ) {
                Row(
                    modifier = modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 12.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier
                            .clip(CircleShape)
                            .size(70.dp)
                            .background(HabitColor.Teal.light)
                            .border(2.dp, Color.Gray, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.avataaar),
                            contentDescription = "Avatar"
                        )
                    }
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp)
                    ) {
                        Text(
                            text = "Backup & Restore",
                            color = Color.White.copy(alpha = 0.95f),
                            fontSize = 21.sp,
                            fontFamily = PoppinsFontFamily,
                        )
                        Spacer(modifier.height(4.dp))
                        Text(
                            text = "Connect your Google account to back up your progress and find your friends",
                            color = Color.White.copy(alpha = 0.80f),
                            lineHeight = 14.sp,
                            fontSize = 10.sp,
                            fontFamily = PoppinsFontFamily,
                        )
                    }
                }
                Spacer(modifier.height(16.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(48.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3F5162),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_icon_logo),
                            contentDescription = "Google logo",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Sign in with Google",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )
                    }
                }
                Spacer(modifier.height(16.dp))
            }

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 250.dp) // min height
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = containerBackgroundDark),
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                ) {
                    Text(
                        modifier = modifier.align(Alignment.CenterHorizontally),
                        text = "Friends",
                        color = Color.White.copy(alpha = 0.95f),
                        fontSize = 21.sp,
                        fontFamily = PoppinsFontFamily,
                    )
                    Spacer(modifier.height(8.dp))

                    val friendsList: List<FriendEntity> = listOf(
                        /*                      FriendEntity("Friend 1", R.drawable.avataaar),
                                              FriendEntity("Friend 2", R.drawable.avataaar),
                                              FriendEntity("Friend 3", R.drawable.avataaar),
                                              FriendEntity("Friend 4", R.drawable.avataaar),*/


                    )

                    // user is not signed in
                    if (!isUserSignedIn) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 250.dp)
                                .background(
                                    Color.Black.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Lock,
                                    contentDescription = null,
                                    tint = Color.White.copy(alpha = 0.8f),
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = "Sign in to access your friends list",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 15.sp,
                                    fontFamily = PoppinsFontFamily
                                )
                                Spacer(Modifier.height(12.dp))
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth(0.6f)
                                        .height(48.dp)
                                        .align(Alignment.CenterHorizontally),
                                    onClick = { },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF3F5162),
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                ) {

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.google_icon_logo),
                                            contentDescription = "Google logo",
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Sign in with Google",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    } else {

                        if (friendsList.isEmpty()) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
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

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {


                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.6f)
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            Brush.linearGradient(
                                                listOf(
                                                    Color(0xFF5865F2),
                                                    Color(0xFF4752C4)
                                                )
                                            )
                                        )
                                        .clickable { /* handle click */ },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Share link",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "or",
                                    color = Color.White.copy(alpha = 0.75f),
                                )

                                Spacer(modifier = Modifier.height(8.dp))


                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextField(
                                        value = text,
                                        onValueChange = { text = it },
                                        placeholder = {
                                            Text(
                                                "Enter friend's ID",
                                                color = Color.Gray
                                            )
                                        },
                                        singleLine = true,
                                        shape = RoundedCornerShape(8.dp),
                                        colors = TextFieldDefaults.colors(
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            errorIndicatorColor = Color.Transparent,
                                            focusedContainerColor = Color(0xFF1E1F22),
                                            unfocusedContainerColor = Color(0xFF1E1F22),
                                            cursorColor = Color.White,
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White
                                        ),
                                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFF1E1F22))
                                    ) {
                                        BasicTextField(
                                            value = text,
                                            onValueChange = { text = it },
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(
                                                    start = 12.dp,
                                                    top = 12.dp,
                                                    bottom = 12.dp
                                                ),
                                            singleLine = true,
                                            textStyle = LocalTextStyle.current.copy(color = Color.White),
                                            decorationBox = { innerTextField ->
                                                if (text.isEmpty()) Text(
                                                    "Enter friend's ID",
                                                    color = Color.Gray
                                                )
                                                innerTextField()
                                            }
                                        )

                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .background(Color(0xD75865F2))
                                                .clickable { /* add click */ }
                                                .padding(horizontal = 16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "ADD",
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            }


                        } else {
                            friendsList.forEach { friend ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 6.dp, vertical = 4.dp)
                                        .background(
                                            Color.White.copy(alpha = 0.08f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    // --- Avatar ---
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(CircleShape)
                                            .background(HabitColor.Teal.light)
                                            .border(2.dp, Color.Gray, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.avataaar),
                                            contentDescription = "Avatar",
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))

                                    // --- Friend name ---
                                    Text(
                                        text = friend.name,
                                        color = Color.White.copy(alpha = 0.92f),
                                        fontSize = 15.sp,
                                        fontFamily = Fonts.Raleway,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
                                    )

                                    // --- Delete icon ---
                                    IconButton(
                                        onClick = { /* #TODO handle remove friend */ },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = "Delete Friend",
                                            tint = Color.White.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}

fun Brush.toColor(): Color = Color.Unspecified // костиль, щоб компілювалося

data class FriendEntity(
    val name: String,
    val image: Int
)

@Preview
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            MeScreen()
        }
    }
}