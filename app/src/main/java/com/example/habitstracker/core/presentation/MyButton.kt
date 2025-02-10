package com.example.habitstracker.core.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    onClick: () -> Unit = { },
    content: @Composable () -> Unit = { Text(text = "Hello World!") },
) {

    Button(
        modifier = modifier
            .padding(padding)
            .height(55.dp)
            .border(
                1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(corner = CornerSize(50.dp))
            ),

        onClick = {
            onClick.invoke()
        },

        colors = ButtonDefaults.buttonColors(containerColor = color),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 16.dp
        )
    ) {
        content()
    }
}