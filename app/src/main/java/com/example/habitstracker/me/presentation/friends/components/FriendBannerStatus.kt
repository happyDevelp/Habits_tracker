package com.example.habitstracker.me.presentation.friends.components

import com.example.habitstracker.me.presentation.component.BannerStatus

enum class FriendsBannerStatus : BannerStatus {
    NONE,
    REQUEST_SENT_SUCCESS,
    USER_NOT_FOUND,
    ALREADY_FRIENDS,
    CANNOT_ADD_SELF,
    REQUEST_FAILED, // general error
    NO_INTERNET
}