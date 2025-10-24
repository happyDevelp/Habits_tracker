package com.example.habitstracker.habit.presentation.create_own_habit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.HabitColor
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import com.example.habitstracker.core.presentation.utils.TestTags

@Composable
fun HabitNameTextField(
    modifier: Modifier = Modifier,
    name: String,
    color: Color,
    onTextChange: (text: String) -> Unit = { },
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = containerBackgroundDark,
        ),
    ) {

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .testTag(TestTags.CREATEHABIT_TEXT_FIELD),
            value = name,
            onValueChange = { newText ->
                if (newText.length < 30)
                    onTextChange(newText)
            },
            textStyle = TextStyle(
                color = Color.White.copy(alpha = 0.85f),
                fontFamily = PoppinsFontFamily,
                fontSize = 18.sp,
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_habit_name),
                    color = Color.White.copy(alpha = 0.75f),
                    fontFamily = PoppinsFontFamily,
                    fontSize = 19.sp,
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(end = 0.dp)
                        .size(20.dp),
                    imageVector = Icons.Default.Edit,
                    contentDescription = null, tint = color
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
        )
    }
}

@Preview
@Composable
private fun Previeww() {

    HabitNameTextField(name = "Fake name", color = HabitColor.Orange.light, onTextChange = { })
}