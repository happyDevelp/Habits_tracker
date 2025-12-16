package com.example.habitstracker.me.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.BuildConfig
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.LocalSettingsSheetController
import com.example.habitstracker.app.SettingsSheetController
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.habit.presentation.today_main.components.SettingsSheet
import com.example.habitstracker.me.domain.model.FriendEntry
import com.example.habitstracker.me.domain.model.FriendRequest
import com.example.habitstracker.me.domain.model.UserProfile
import com.example.habitstracker.me.presentation.component.AccountSettingsBottomSheet
import com.example.habitstracker.me.presentation.component.LoadingOverlay
import com.example.habitstracker.me.presentation.component.MeTopBar
import com.example.habitstracker.me.presentation.component.NotificationDialog
import com.example.habitstracker.me.presentation.component.TopBanner
import com.example.habitstracker.me.presentation.component.UserCardSection
import com.example.habitstracker.me.presentation.friends.FriendsState
import com.example.habitstracker.me.presentation.friends.FriendsViewModel
import com.example.habitstracker.me.presentation.friends.components.AddFriendSection
import com.example.habitstracker.me.presentation.friends.components.DeleteFriendDialog
import com.example.habitstracker.me.presentation.friends.components.FriendStatsDialog
import com.example.habitstracker.me.presentation.friends.components.FriendsEmptyState
import com.example.habitstracker.me.presentation.friends.components.FriendsHeader
import com.example.habitstracker.me.presentation.friends.components.FriendsList
import com.example.habitstracker.me.presentation.friends.components.shareFriendLink
import com.example.habitstracker.me.presentation.sign_in.NotSignedInState
import com.example.habitstracker.me.presentation.sign_in.SignInState
import com.example.habitstracker.me.presentation.sign_in.SignInViewModel
import com.example.habitstracker.me.presentation.sign_in.UserData
import com.example.habitstracker.me.presentation.sign_in.getAccountButtons
import com.example.habitstracker.me.presentation.sync.SyncState
import com.example.habitstracker.me.presentation.sync.SyncViewModel
import kotlinx.coroutines.launch

@Composable
fun MeScreenRoot(
    signInViewModel: SignInViewModel = hiltViewModel<SignInViewModel>(),
    syncViewModel: SyncViewModel = hiltViewModel<SyncViewModel>(),
    friendsViewModel: FriendsViewModel = hiltViewModel<FriendsViewModel>()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val signInState by signInViewModel.state.collectAsStateWithLifecycle()
    val syncState by syncViewModel.state.collectAsStateWithLifecycle()
    val profileState by syncViewModel.profileState.collectAsStateWithLifecycle()
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
        signInState = signInState,
        syncState = syncState,
        profileState = profileState,
        friendsState = friendsState,
        isLoading = signInState.isLoading || syncState.isLoading,
        onSignInClick = { signInViewModel.signIn() },
        onSignOutClick = { signInViewModel.signOut() },
        clearCloudData = { syncViewModel.clearCloudData() },
        syncToCloud = { syncViewModel.syncToCloud() },
        syncFromCloud = { syncViewModel.syncFromCloud() },

        // friends
        onAddFriendClick = { friendsViewModel.onAddFriendClicked(it) },
        onAcceptRequestClick = friendsViewModel::onAcceptRequest,
        onRejectRequestClick = friendsViewModel::onRejectRequest,
        onShareLinkClick = {
            shareFriendLink(
                context = context,
                profileState.profileCode
            )
        },
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
    signInState: SignInState,
    syncState: SyncState,
    profileState: UserProfile,
    friendsState: FriendsState,
    isLoading: Boolean,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit,
    clearCloudData: () -> Unit,
    syncToCloud: () -> Unit,
    syncFromCloud: () -> Unit,

    //friends
    onAcceptRequestClick: (FriendRequest) -> Unit,
    onRejectRequestClick: (FriendRequest) -> Unit,
    onAddFriendClick: (String) -> Unit,
    onShareLinkClick: () -> Unit,
    getFriendStats: (String) -> Unit,
    onDeleteFriendClick: (String) -> Unit
) {
    val settingsController = LocalSettingsSheetController.current
    val settingsSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
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
        containerColor = screenBackgroundDark,
        topBar = {
            MeTopBar(
                notificationBoxOpen = {
                    @Suppress("AssignedValueIsNeverRead")
                    openNotificationBox = true
                },
                badgeCount = friendsState.incomingRequests.size
            )
        },
        // slot for the bottom
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                MyText(
                    text = "version " + BuildConfig.VERSION_NAME,
                    textSize = 12.sp,
                    color = Color.White.copy(0.6f)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // User card
            UserCardSection(
                modifier = Modifier,
                syncToCloud = syncToCloud,
                user = signInState.userData,
                openBottomSheet = { openButtonsSheet = true },
                rotation = rotation,
                lastSync = syncState.lastSync,
                syncInProgress = syncState.syncInProgress,
                onSignInClick = onSignInClick
            )

            // friends section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 250.dp)
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = containerBackgroundDark),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.90f)
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                ) {
                    // --- friends HEADER --
                    FriendsHeader(signInState, profileState)

                    // --- NOT SIGNED IN ------------------------------------------------------
                    if (signInState.userData == null) {
                        NotSignedInState(
                            onSignInClick = onSignInClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(
                                    Color.Black.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        )
                        return@Column
                    }

                    // friends section when signedIn
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {
                        if (friendsState.friends.isEmpty()) {
                            FriendsEmptyState()
                        } else {
                            FriendsList(
                                friendsState = friendsState,
                                openStatsDialog = { openStatsDialog = true },
                                selectedFriend = { selectedFriend = it },
                                openFriendDeleteConfirmation = {
                                    openFriendDeleteConfirmation = true
                                },
                                getFriendStats = getFriendStats
                            )
                        }
                    }
                    Spacer(Modifier.height(22.dp))

                    // --- bottom section: ADD / or / Share link (if the user is logged in) ---
                    AddFriendSection(onAddFriendClick, onShareLinkClick)
                }
            }

            if (settingsController.isOpen) {
                SettingsSheet(sheetState = settingsSheetState)
            }

            val accountButtons by remember {
                getAccountButtons(
                    { openButtonsSheet = false },
                    syncFromCloud,
                    onSignOutClick
                )
            }

            if (openButtonsSheet) {
                AccountSettingsBottomSheet(
                    sheetState = settingsSheetState,
                    buttons = accountButtons,
                    onCloudDataClear = clearCloudData,
                    closeBottomSheet = { openButtonsSheet = false }
                )
            }

            if (openNotificationBox) {
                NotificationDialog(
                    requests = friendsState.incomingRequests,
                    onAccept = { onAcceptRequestClick(it) },
                    onIgnore = { onRejectRequestClick(it) },
                    onDismiss = { openNotificationBox = false }
                )
            }

            if (openStatsDialog) {
                FriendStatsDialog(
                    stats = friendsState.friendStats,
                    friend = selectedFriend ?: FriendEntry(),
                    closeFriendStats = {
                        openStatsDialog = false
                        selectedFriend = null
                    }
                )
            }

            if (openFriendDeleteConfirmation && selectedFriend != null) {
                DeleteFriendDialog(
                    friend = selectedFriend!!,
                    onConfirm = {
                        onDeleteFriendClick(selectedFriend!!.friendUserId)
                        openFriendDeleteConfirmation = false
                        selectedFriend = null
                    }, onDismiss = {
                        openFriendDeleteConfirmation = false
                        selectedFriend = null
                    }
                )
            }
        }
    }
    if (isLoading) {
        LoadingOverlay()
    }
    TopBanner(status = signInState.banner)
    TopBanner(status = syncState.banner)
    TopBanner(status = friendsState.banner)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    val mockSettingsController = remember { SettingsSheetController() }

    CompositionLocalProvider(
        LocalNavController provides mockNavController,
        LocalSettingsSheetController provides mockSettingsController,
    ) {
        AppTheme(darkTheme = true) {
            val user = UserData(
                "1",
                email = "12345@gmail.com",
                userName = "test name",
                profilePictureUrl = null
            )
            MeScreen(
                onSignInClick = { },
                onSignOutClick = { },
                clearCloudData = { },
                isLoading = false,
                syncToCloud = {},
                syncFromCloud = {},
                onAddFriendClick = {},
                onShareLinkClick = {},
                onAcceptRequestClick = {},
                onRejectRequestClick = {},
                getFriendStats = { },
                onDeleteFriendClick = {},
                profileState = UserProfile(
                    displayName = "TEST name", profileCode = "1234567890",
                    avatarUrl = "hr01"
                ),
                signInState = SignInState(loginSuccessful = true, userData = user),
                friendsState = FriendsState(
                    friends = listOf(
                        FriendEntry(
                            friendUserId = "1234567890", friendDisplayName = "test friend 1",
                            friendAvatarUrl = null, friendSince = 1765196168851
                        ),
                    )
                ),
                syncState = SyncState()
            )
        }
    }
}