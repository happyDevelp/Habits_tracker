package com.example.habitstracker.habit.presentation.create_own_habit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.MyButton
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.ColorGradient
import com.example.habitstracker.core.presentation.theme.HabitColor
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun ColorPickerPreview() {
    AppTheme(darkTheme = true) {
        ColorPicker()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    closingSheet: () -> Unit = { },
    clickAddColor: (Color) -> Unit = { },
) {

    Box(contentAlignment = Alignment.BottomCenter) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val scope = rememberCoroutineScope()

        val colorList = listOf(
            HabitColor.SkyBlue,
            HabitColor.LeafGreen,
            HabitColor.Amber,
            HabitColor.DeepBlue,
            HabitColor.BrickRed,
            //HabitColor.Cyan,
            HabitColor.Orange,
            HabitColor.Teal,
            HabitColor.Golden,
            HabitColor.Lime,
            //HabitColor.Aqua,
            HabitColor.Purple,
            //HabitColor.Terracotta,
            HabitColor.Rose,
            //HabitColor.DarkGreen,
            HabitColor.Sand,
        )

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                closingSheet.invoke()
            },
            dragHandle = null,
            containerColor = containerBackgroundDark
        ) {

            Box(
                modifier = modifier,
                contentAlignment = Alignment.BottomCenter
            ) {

                // Send icon to [CreateOwnHabitScreen]
                var pickedGradient by remember {
                    mutableStateOf<ColorGradient?>(null) }

                var selectedGradient by remember {
                    mutableStateOf<ColorGradient?>(null)
                }

                LazyVerticalGrid(
                    modifier = modifier.padding(bottom = 85.dp, top = 16.dp),
                    columns = GridCells.Fixed(4),
                    //verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    items(colorList) { gradient ->

                        val isSelected = gradient == selectedGradient

                        IconButton(
                            modifier = modifier
                                .height(100.dp)
                                .padding(16.dp)
                                .clip(RoundedCornerShape(size = 8.dp))
                                .background(
                                    if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent
                                //if (isSelected) currentColor.copy(alpha = 0.4f) else Color.Transparent
                                ),
                            onClick = {
                                selectedGradient = gradient
                                pickedGradient = gradient
                            },

                            ) {
                            Box(
                                modifier = modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(size = 12.dp))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(gradient.light, gradient.dark)
                                        )
                                    )
                            )
                        }

                    }
                }

                Row(
                    modifier.padding(all = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    MyButton(
                        modifier = modifier.weight(1f),
                        color = containerBackgroundDark,
                        onClick = {
                            closingSheet.invoke()
                            scope.launch { sheetState.hide() }
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }

                    MyButton(
                        modifier = modifier.weight(1f),
                        onClick = {
                            pickedGradient?.let { clickAddColor.invoke(pickedGradient!!.light) }
                            closingSheet.invoke()
                            scope.launch { sheetState.hide() }
                        }
                    ) {
                        Text(
                            text = "Add",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}