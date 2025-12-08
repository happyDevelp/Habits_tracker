package com.example.habitstracker.me.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.me.domain.model.FriendEntry
import com.example.habitstracker.me.domain.model.FriendRequest
import com.example.habitstracker.me.domain.model.UserStats
import com.example.habitstracker.me.presentation.component.AccountButton
import com.example.habitstracker.me.presentation.component.AccountSettingsBottomSheet
import com.example.habitstracker.me.presentation.component.BannerStatus
import com.example.habitstracker.me.presentation.component.CopyIdChip
import com.example.habitstracker.me.presentation.component.LoadingOverlay
import com.example.habitstracker.me.presentation.component.MeTopBar
import com.example.habitstracker.me.presentation.component.MyAlertDialog
import com.example.habitstracker.me.presentation.component.NotificationDialog
import com.example.habitstracker.me.presentation.component.SignInButton
import com.example.habitstracker.me.presentation.component.TopBanner
import com.example.habitstracker.me.presentation.component.userCard
import com.example.habitstracker.me.presentation.friends.FriendsViewModel
import com.example.habitstracker.me.presentation.friends.components.FriendListItem
import com.example.habitstracker.me.presentation.friends.components.FriendStatsDialog
import com.example.habitstracker.me.presentation.sign_in.SignInBannerStatus
import com.example.habitstracker.me.presentation.sign_in.SignInViewModel
import com.example.habitstracker.me.presentation.sign_in.UserData
import com.example.habitstracker.me.presentation.sync.SyncBannerStatus
import com.example.habitstracker.me.presentation.sync.SyncViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun MeScreenRoot(
    signInViewModel: SignInViewModel = hiltViewModel<SignInViewModel>(),
    syncViewModel: SyncViewModel = hiltViewModel<SyncViewModel>(),
    friendsViewModel: FriendsViewModel = hiltViewModel<FriendsViewModel>()
) {
    val coroutineScope = rememberCoroutineScope()
    val signInState by signInViewModel.state.collectAsStateWithLifecycle()
    val syncState by syncViewModel.state.collectAsStateWithLifecycle()
    val userProfile by syncViewModel.profileState.collectAsStateWithLifecycle()
    val friendsState by friendsViewModel.state.collectAsStateWithLifecycle()

    // As soon as the login is successful, launch syncFromCloud
    LaunchedEffect(signInState.loginSuccessful) {
        if (signInState.loginSuccessful == true) {
            syncViewModel.loadUserProfile()
            syncViewModel.syncFromCloud()
            signInViewModel.resetLoginSuccessful()
        }
    }

    MeScreen(
        user = signInState.userData,
        profileId = userProfile?.profileCode ?: "",
        isLoading = signInState.isLoading || syncState.isLoading,
        bannerStatus = signInState.banner,
        syncBannerState = syncState.banner,
        lastSync = syncState.lastSync,
        syncInProgress = syncState.syncInProgress,
        onSignInClick = { signInViewModel.signIn() },
        onSignOutClick = { signInViewModel.signOut() },
        clearCloudData = { syncViewModel.clearCloudData() },
        syncToCloud = { syncViewModel.syncToCloud() },
        syncFromCloud = { syncViewModel.syncFromCloud() },

        // friends
        friendsList = friendsState.friends,
        friendStats = friendsState.friendStats ?: UserStats(),
        pendingRequests = friendsState.incomingRequests,
        onAddFriendClick = { friendsViewModel.onAddFriendClicked(it) },
        onAcceptRequestClick = friendsViewModel::onAcceptRequest,
        onRejectRequestClick = friendsViewModel::onRejectRequest,
        onShareLinkClick = { /* shareIntent with friendCode */ },
        getFriendStats = {
            coroutineScope.launch {
                friendsViewModel.getFriendStats(it)
            }
        },
        onDeleteFriendClick = {
            friendsViewModel.deleteFriend(it)
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(
    modifier: Modifier = Modifier,
    user: UserData?,
    profileId: String,
    isLoading: Boolean,
    bannerStatus: BannerStatus,
    syncBannerState: BannerStatus,
    lastSync: String?,
    syncInProgress: Boolean,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit,
    clearCloudData: () -> Unit,
    syncToCloud: () -> Unit,
    syncFromCloud: () -> Unit,

    //friends
    friendStats: UserStats,
    friendsList: List<FriendEntry>,
    pendingRequests: List<FriendRequest>,
    onAcceptRequestClick: (FriendRequest) -> Unit,
    onRejectRequestClick: (FriendRequest) -> Unit,
    onAddFriendClick: (String) -> Unit,
    onShareLinkClick: () -> Unit,
    getFriendStats: (String) -> Unit,
    onDeleteFriendClick: (String) -> Unit
) {
    var typedText by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var openButtonsSheet by remember { mutableStateOf(false) }
    var openNotificationBox by remember { mutableStateOf(false) }
    var openStatsDialog by remember { mutableStateOf(false) }
    var openFriendDeleteConfirmation by remember { mutableStateOf(false) }

    var selectedFriend by remember { mutableStateOf<FriendEntry?>(null) }

    val rotation by animateFloatAsState(
        targetValue = if (openButtonsSheet) 180f else 0f,
        label = "arrowRotation"
    )

    Scaffold(
        topBar = {
            MeTopBar(
                notificationBoxOpen = { openNotificationBox = true },
                badgeCount = pendingRequests.size
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(screenBackgroundDark)
        ) {
            // User card
            userCard(
                modifier = Modifier,
                syncToCloud,
                user,
                openBottomSheet = { openButtonsSheet = true },
                rotation,
                lastSync,
                syncInProgress,
                onSignInClick
            )

            // friends section
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 250.dp)
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = containerBackgroundDark),
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.90f)
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = modifier.align(Alignment.Center),
                            text = "Friends",
                            color = Color.White.copy(alpha = 0.95f),
                            fontSize = 21.sp,
                            fontFamily = PoppinsFontFamily,
                        )
                        if (user != null) {
                            CopyIdChip(
                                profileCode = profileId,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    }
                    Spacer(modifier.height(16.dp))

                    // --- NOT SIGNED IN ------------------------------------------------------
                    if (user == null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
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
                                SignInButton { onSignInClick() }
                            }
                        }
                        return@Column
                    }

                    // friends section when signedIn
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {
                        if (friendsList.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(bottom = 18.dp)
                                    .align(Alignment.Center),
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
                        } else {

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 700.dp),
                                contentPadding = PaddingValues(
                                    horizontal = 6.dp,
                                    vertical = 4.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                items(friendsList) { friend ->
                                    FriendListItem(
                                        name = friend.friendDisplayName,
                                        avatarUrl = friend.friendAvatarUrl,
                                        onClick = {
                                            openStatsDialog = true
                                            getFriendStats(friend.friendUserId)
                                            selectedFriend = friend
                                        },
                                        onDeleteClick = {
                                            selectedFriend = friend
                                            openFriendDeleteConfirmation = true
                                        },
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(22.dp))

                    // --- bottom section: Share link / or / ADD (if the user is logged in) ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val interactionSourceAddButton = remember { MutableInteractionSource() }
                        val isPressedAddButton by interactionSourceAddButton.collectIsPressedAsState()
                        val scaleAddButton by animateFloatAsState(
                            targetValue = if (isPressedAddButton) 0.96f else 1f,
                            label = "scaleButton"
                        )
                        Row(
                            modifier = Modifier
                                .weight(1.5f)
                                .height(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF1E1F22)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .graphicsLayer {
                                        scaleX = scaleAddButton
                                        scaleY = scaleAddButton
                                    }
                                    .fillMaxHeight()
                                    .background(Color(0xD75865F2))
                                    .clickable(
                                        interactionSource = interactionSourceAddButton,
                                        indication = null
                                    ) { onAddFriendClick(typedText) }
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "ADD",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            BasicTextField(
                                value = typedText,
                                onValueChange = { typedText = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 10.dp, top = 8.dp, bottom = 8.dp),
                                singleLine = true,
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.White,
                                    fontSize = 13.sp
                                ),
                                decorationBox = { innerTextField ->
                                    if (typedText.isEmpty()) {
                                        Text(
                                            text = "Enter friend's ID",
                                            color = Color.Gray,
                                            fontSize = 13.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "or",
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        val interSourceShareLink = remember { MutableInteractionSource() }
                        val isPressedShareLink by interSourceShareLink.collectIsPressedAsState()
                        val scaleSharedLink by animateFloatAsState(
                            targetValue = if (isPressedShareLink) 0.96f else 1f,
                            label = "scaleSharedLinkButton"
                        )
                        Box(
                            modifier = Modifier
                                .graphicsLayer {
                                    scaleX = scaleSharedLink
                                    scaleY = scaleSharedLink
                                }
                                .clickable(
                                    interactionSource = interSourceShareLink,
                                    indication = null
                                ) {
                                    /*TODO share link on click*/
                                }
                                .height(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            Color(0xFF5865F2),
                                            Color(0xFF4752C4)
                                        )
                                    )
                                )
                                .padding(horizontal = 16.dp)
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Share link",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            val buttons by remember {
                mutableStateOf(
                    listOf(
                        AccountButton(
                            "Upload Data From Cloud",
                            Icons.Outlined.CloudUpload,
                            onClick = {
                                openButtonsSheet = false
                                syncFromCloud()
                            }
                        ),
                        AccountButton(
                            "Log Out",
                            Icons.Outlined.Logout,
                            onClick = {
                                openButtonsSheet = false
                                onSignOutClick()
                            }
                        ),
                        AccountButton(
                            "Clear Cloud Data",
                            Icons.Outlined.DeleteForever,
                            color = Color(0xFFFF1000),
                            onClick = { }
                        )
                    )
                )
            }
            if (openButtonsSheet) {
                AccountSettingsBottomSheet(
                    sheetState = sheetState,
                    buttons = buttons,
                    onCloudDataClear = clearCloudData,
                    closeBottomSheet = { openButtonsSheet = false }
                )
            }

            if (openNotificationBox) {
                NotificationDialog(
                    requests = pendingRequests,
                    /*listOf(
                            FriendRequestUi(
                                id = "1",
                                name = "John Doe",
                                avatarUrl = null
                            ),
                            FriendRequestUi(
                                id = "12",
                                name = "Matteus Müller",
                                avatarUrl = null
                            ))*/
                    onAccept = { onAcceptRequestClick(it) },
                    onIgnore = { onRejectRequestClick(it) },
                    onDismiss = { openNotificationBox = false }
                )
            }

            if (openStatsDialog) {
                FriendStatsDialog(
                    stats = friendStats,
                    friend = selectedFriend ?: FriendEntry(),
                    closeFriendStats = {
                        openStatsDialog = false
                        selectedFriend = null
                    }
                )
            }

            if (openFriendDeleteConfirmation) {
                selectedFriend?.let { friendToDelete ->
                    // 1. Create a "smart" string
                    val styledMessage = buildAnnotatedString {
                        // First part (grayed by default from dialog)
                        append("Are you sure you want to remove ")

                        withStyle( // Part with the name (White and Bold)
                            style = SpanStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(friendToDelete.friendDisplayName)
                        }
                        append(" from friends?") // End of sentence
                    }
                    MyAlertDialog(
                        title = "Delete friend",
                        annotatedMessage = styledMessage,
                        onConfirm = {
                            onDeleteFriendClick(friendToDelete.friendUserId)
                            openFriendDeleteConfirmation = false
                            selectedFriend = null
                        },
                        onDismiss = {
                            openFriendDeleteConfirmation = false
                            selectedFriend = null
                        }
                    )
                }
            }
        }
    }
    if (isLoading) {
        LoadingOverlay()
    }

    TopBanner(
        status = bannerStatus,
    )

    TopBanner(
        status = syncBannerState,
    )
}

@Preview
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            val user = UserData(
                "1",
                email = "12345@gmail.com",
                userName = "test name",
                profilePictureUrl = null
            )
            MeScreen(
                user = /*null*/ user,
                onSignInClick = { },
                onSignOutClick = { },
                clearCloudData = { },
                isLoading = false,
                bannerStatus = SignInBannerStatus.NONE,
                syncToCloud = {},
                syncFromCloud = {},
                syncBannerState = SyncBannerStatus.NONE,
                lastSync = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                syncInProgress = true,
                profileId = "GPAA-JOJA",
                friendsList = listOf(
                    FriendEntry(
                        friendDisplayName = "Matteus Müller",
                        friendAvatarUrl = null,
                        friendUserId = "KFBA-W921",
                        friendSince = 123456
                    ),
                    FriendEntry(
                        friendDisplayName = "Jon Doe",
                        friendAvatarUrl = null,
                        friendUserId = "GPAA-JOJA",
                        friendSince = 123456
                    ),
                    FriendEntry(
                        friendDisplayName = "Mattew Himars",
                        friendAvatarUrl = null,
                        friendUserId = "GPAA-JOJA",
                        friendSince = 123456
                    ),

                    ),
                pendingRequests = emptyList(),
                onAddFriendClick = {},
                onShareLinkClick = {},
                onAcceptRequestClick = {},
                onRejectRequestClick = {},
                getFriendStats = { },
                friendStats = UserStats(),
                onDeleteFriendClick = {}
            )
        }
    }
}