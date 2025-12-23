package com.example.habitstracker.profile.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.HabitColor
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import com.example.habitstracker.profile.presentation.sign_in.UserData
import com.example.habitstracker.profile.presentation.sync.components.SyncIcon

@Composable
fun UserCardSection(
    modifier: Modifier,
    syncToCloud: () -> Unit,
    user: UserData?,
    openBottomSheet: () -> Unit,
    rotation: Float,
    lastSync: String?,
    syncInProgress: Boolean,
    onSignInClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { syncToCloud() },
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
                    .padding()
            ) {
                // userAvatar
                AsyncImage(
                    model = user?.profilePictureUrl,
                    contentDescription = "User avatar",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.avataaar), // is shown when downloading
                    error = painterResource(R.drawable.avataaars)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = modifier
                        .wrapContentWidth()
                        .padding(start = 12.dp),
                    verticalArrangement = Arrangement.Center,
                ) {

                    if (user == null) {
                        Text(
                            text = stringResource(R.string.backup_restore),
                            color = Color.White.copy(alpha = 0.95f),
                            fontSize = 21.sp,
                            fontFamily = PoppinsFontFamily,
                        )

                        Text(
                            text = stringResource(R.string.conntect_to_backup_text),
                            color = Color.White.copy(alpha = 0.80f),
                            lineHeight = 14.sp,
                            fontSize = 10.sp,
                            fontFamily = PoppinsFontFamily,
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.welcome_back),
                            color = Color.White.copy(alpha = 0.90f),
                            fontSize = 14.sp,
                            fontFamily = PoppinsFontFamily,
                        )
                        Spacer(modifier.height(0.dp))
                        Row(
                            modifier = Modifier
                                .clickable { openBottomSheet() },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${user.userName}",
                                color = Color.White.copy(alpha = 0.95f),
                                fontSize = 21.sp,
                                fontFamily = PoppinsFontFamily,
                            )
                            Icon(
                                modifier = Modifier
                                    .padding()
                                    .graphicsLayer {
                                        rotationZ = rotation
                                    },
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Account Settings Menu",
                                tint = Color.White.copy(alpha = 0.9f),
                            )
                        }

                        Spacer(modifier.height(2.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = lastSync?.let {
                                    "last synchronized: $it"
                                } ?: "Sync failed",
                                fontFamily = PoppinsFontFamily,
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp
                            )
                            Spacer(modifier.width(4.dp))

                            SyncIcon(syncInProcess = syncInProgress)
                        }
                    }
                }
            }
        }

        // Sign in with Google
        if (user == null) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SignInButton(onSignInClick = onSignInClick)
            }
        }
        Spacer(modifier.height(12.dp))
    }
}

@Composable
private fun Preview(modifier: Modifier = Modifier) {
/*    UserCardSection(syncToCloud = {}, user = null, openBottomSheet = {}, rotation = 0f,
        lastSync = null, syncInProgress = false, onSignInClick = {}) { }*/
}