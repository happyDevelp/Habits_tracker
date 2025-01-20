package com.example.habitstracker.habit.presentation.edit_habit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.utils.TestTags

@Composable
fun EditHabitNameTextField(
    modifier: Modifier = Modifier,
    name: String,
    onTextChange: (text: String) -> Unit = {  },
) {
    TextField(
        modifier = modifier.fillMaxWidth().testTag(TestTags.CREATEHABIT_TEXT_FIELD),
        value = name,
        onValueChange = { newText ->
            if (newText.length < 30)
                onTextChange(newText)
        },

        textStyle = TextStyle(
            color = Color.White.copy(alpha = 0.85f),
            fontFamily = PoppinsFontFamily,
            fontSize = 22.sp,
        ),

        trailingIcon = {
            Icon(
                modifier = Modifier.padding(end = 0.dp),
                imageVector = Icons.Default.Edit, contentDescription = null
            )
        },

        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.Transparent,
        ),

        singleLine = true,
    )
}