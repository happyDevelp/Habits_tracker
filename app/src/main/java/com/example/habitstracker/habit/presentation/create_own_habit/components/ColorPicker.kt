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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.MyButton
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
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

    Surface {

        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()

        val colorList = listOf<Color>(
            Color(0xFF068FF7),
            Color(0xFF259705),
            Color(0xFFFCA800),
            Color(0xFF005EFF),
            Color(0xFFB13423),
            Color(0xFF01ADD8),
            Color(0xFFFF7700),
            Color(0xFF00A092),
            Color(0xFFFFB700),
            Color(0xFF8BC700),
            Color(0xFF61CED8),
            Color(0xFF8F62C4),
            Color(0xFFCE6849),
            Color(0xFFCC5A7B),
            Color(0xFF127700),
            Color(0xFFC2AC5E),
        )

        ModalBottomSheet(
            modifier = modifier.fillMaxHeight(0.5f),
            sheetState = sheetState,
            onDismissRequest = {
                closingSheet.invoke()
            },
            dragHandle = null,
            containerColor = screenContainerBackgroundDark
        ) {

            Box(
                modifier = modifier,
                contentAlignment = Alignment.BottomCenter
            ) {

                // Send icon to [CreateOwnHabitScreen]
                var pickedColor by remember {
                    mutableStateOf(Color.Transparent)
                }

                var selectedColor by remember {
                    mutableStateOf<Color?>(null)
                }

                LazyVerticalGrid(
                    modifier = modifier.padding(bottom = 85.dp, top = 16.dp),
                    columns = GridCells.Fixed(4),
                    //verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    items(colorList) { currentColor ->

                        val isSelected = currentColor == selectedColor

                        IconButton(
                            modifier = modifier
                                .height(100.dp)
                                .padding(16.dp)
                                .clip(RoundedCornerShape(size = 8.dp))
                                .background(
                                    if (isSelected) currentColor.copy(alpha = 0.4f)
                                    else Color.Transparent
                                ),
                            onClick = {
                                selectedColor = currentColor

                                pickedColor = currentColor
                            },

                            ) {
                            Box(
                                modifier = modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(size = 12.dp))
                                    .background(currentColor)
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
                        color = screenContainerBackgroundDark,
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
                            clickAddColor.invoke(pickedColor)
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