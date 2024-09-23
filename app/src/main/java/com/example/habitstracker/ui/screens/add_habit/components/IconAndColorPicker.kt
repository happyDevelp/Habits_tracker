package com.example.habitstracker.ui.screens.add_habit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.habitstracker.ui.screens.create_own_habit.components.ColorPicker
import com.example.habitstracker.ui.screens.create_own_habit.components.IconPicker
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import com.example.habitstracker.ui.theme.screenContainerBackgroundDark
import com.example.habitstracker.utils.clickWithRipple

@Composable
fun IconAndColorPicker(modifier: Modifier = Modifier) {
    var currentColor: Color by remember {
        mutableStateOf(Color(0xFFE26A19))
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color = screenContainerBackgroundDark),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        var currentIcon by remember {
            mutableStateOf(Icons.Filled.SentimentVerySatisfied)
        }

        IconButton(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(0.18f),
            onClick = { /*TODO*/ },
        ) {
            Icon(
                modifier = modifier.size(32.dp),
                imageVector = currentIcon,
                contentDescription = "Select icon",
                tint = currentColor
            )
        }

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            var isIconSheetOpen by remember {
                mutableStateOf(false)
            }

            if (isIconSheetOpen) {
                IconPicker(
                    closingSheet = { isIconSheetOpen = false },
                    clickAddIcon = { selectedIcon ->
                        currentIcon = selectedIcon
                    }
                )
            }
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clickWithRipple(color = Color.White) {
                        isIconSheetOpen = true
                    },

                contentAlignment = Alignment.CenterStart,
            ) {
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    text = "Icon",
                    fontFamily = PoppinsFontFamily,
                    color = Color.White,
                )

                Icon(
                    modifier = modifier
                        .align(Alignment.CenterEnd)
                        .padding(12.dp),
                    imageVector = Icons.Default.ChevronRight,
                    tint = Color.White.copy(0.70f),
                    contentDescription = null
                )
            }

            HorizontalDivider(
                thickness = 0.25.dp,
                color = Color.White
            )


            var isColorSheetOpen by remember {
                mutableStateOf(false)
            }
            if (isColorSheetOpen) {
                ColorPicker(
                    closingSheet = { isColorSheetOpen = false },
                    clickAddColor = { selectedColor ->
                        currentColor = selectedColor
                    }
                )
            }
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clickWithRipple(color = Color.White) {
                        isColorSheetOpen = true
                    },
                contentAlignment = Alignment.CenterStart
            ) {

                Text(
                    modifier = modifier.padding(start = 8.dp),

                    text = "Color",
                    fontFamily = PoppinsFontFamily,
                    color = Color.White,
                )

                Row(
                    modifier = modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = modifier
                            .size(14.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(currentColor)
                            .width(150.dp)
                    )

                    Spacer(modifier = modifier.width(14.dp))

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        tint = Color.White.copy(0.70f),
                        contentDescription = null
                    )
                }
            }
        }
    }
}