package com.example.habitstracker.profile.presentation.friends.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitstracker.profile.domain.model.FriendEntry
import com.example.habitstracker.profile.presentation.friends.FriendsState

@Composable
fun FriendsList(
    friendsState: FriendsState,
    openStatsDialog: () -> Unit,
    selectedFriend: (FriendEntry) -> Unit,
    getFriendStats: (String) -> Unit,
    openFriendDeleteConfirmation: () -> Unit,
) {


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
        items(friendsState.friends) { friend ->
            FriendListItem(
                name = friend.friendDisplayName,
                avatarUrl = friend.friendAvatarUrl,
                onClick = {
                    openStatsDialog()
                    getFriendStats(friend.friendUserId)
                    selectedFriend(friend)
                },
                onDeleteClick = {
                    selectedFriend(friend)
                    openFriendDeleteConfirmation()
                },
            )
        }
    }
}