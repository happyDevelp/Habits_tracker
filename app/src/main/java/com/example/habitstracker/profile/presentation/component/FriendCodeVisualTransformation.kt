package com.example.habitstracker.profile.presentation.component

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class FriendCodeVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Crop to 8 characters
        val trimmed = if (text.text.length >= 8) text.text.substring(0, 8) else text.text

        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 3) out += "-" // Insert a hyphen after the 4th character
        }

        val offsetMapping = object : OffsetMapping {

            // This function is responsible for where to draw the cursor
            override fun originalToTransformed(offset: Int): Int {
                // If we typed 0, 1, 2 or 3 characters, the cursor goes normally
                if (offset <= 3) return offset

                // Once we have typed the 4th character (offset == 4) or more,
                // Add +1 to the cursor position to jump over the hyphen.
                // That is, for the original index of 4, the cursor will become 5.
                if (offset <= 8) return offset + 1

                return 9
            }

            // This function is needed for correct deletion (backspace) and clicks
            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                return 8
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}