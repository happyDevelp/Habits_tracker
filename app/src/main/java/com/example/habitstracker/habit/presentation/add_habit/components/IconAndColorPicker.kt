package com.example.habitstracker.habit.presentation.add_habit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import com.example.habitstracker.core.presentation.utils.clickWithRipple
import com.example.habitstracker.core.presentation.utils.getIconName
import com.example.habitstracker.habit.presentation.create_own_habit.components.ColorPicker
import com.example.habitstracker.habit.presentation.create_own_habit.components.IconPicker

@Composable
fun IconAndColorPicker(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    color: Color,
    onIconPick: (icon: String) -> Unit,
    onColorPick: (color: Color) -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(18.dp))
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var isIconSheetOpen by remember {
            mutableStateOf(false)
        }

        if (isIconSheetOpen) {
            IconPicker(
                onCloseClick = { isIconSheetOpen = false },
                onAddIconClick = { selectedIcon ->
                    onIconPick(getIconName(selectedIcon))
                },
                color = color
            )
        }

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f)
                .background(containerBackgroundDark)
                .clickWithRipple(color = Color.White) {
                    isIconSheetOpen = true
                }
                .padding(horizontal = 12.dp, vertical = 8.dp),

            contentAlignment = Alignment.CenterStart,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.2f))
                    .align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                modifier = modifier
                    .padding(start = 24.dp)
                    .align(Alignment.Center),
                text = stringResource(R.string.icon),
                fontFamily = PoppinsFontFamily,
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(Modifier.width(8.dp))


        var isColorSheetOpen by remember {
            mutableStateOf(false)
        }
        if (isColorSheetOpen) {
            ColorPicker(
                closingSheet = { isColorSheetOpen = false },
                clickAddColor = { selectedColor ->
                    onColorPick(selectedColor)
                }
            )
        }

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f)
                .background(containerBackgroundDark)
                .clickWithRipple(color = Color.White) {
                    isColorSheetOpen = true
                }
                .padding(horizontal = 12.dp, vertical = 8.dp),

            contentAlignment = Alignment.CenterStart,
        ) {


            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent)
                    .align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = modifier
                        .size(18.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(color)
                        .width(150.dp)
                )
            }

            Text(
                modifier = modifier
                    .padding(start = 24.dp)
                    .align(Alignment.Center),
                text = stringResource(R.string.color),
                fontFamily = PoppinsFontFamily,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    IconAndColorPicker(
        modifier = Modifier,
        icon = Icons.Default.ChevronRight,
        color = Color.White,
        onIconPick = { }, onColorPick = { }
    )
}