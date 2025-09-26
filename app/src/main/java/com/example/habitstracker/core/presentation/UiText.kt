package com.example.habitstracker.core.presentation

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    class StringResources(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UiText()

    @Composable
    fun asString(): String {
        // this here, is an instant of actual UiText class
        return when(this) {
            is DynamicString -> value
            is StringResources -> stringResource(id = resId, formatArgs = *args)
        }
    }

    fun asString(context: Context): String {
        // this here, is an instant of actual UiText class
        return when(this) {
            is DynamicString -> value
            is StringResources -> context.getString(resId, *args)
        }
    }
}