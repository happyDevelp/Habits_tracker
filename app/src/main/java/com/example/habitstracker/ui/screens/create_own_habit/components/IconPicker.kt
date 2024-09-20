package com.example.habitstracker.ui.screens.create_own_habit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.ui.custom.MyButton
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.thirtyContainerDark
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun IconPickerPreview() {
    AppTheme(darkTheme = true) {
        IconPicker()
    }
}

data class IconItem(
    var name: String = "",
    var image: ImageVector? = null,
    var selected: Boolean = false,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconPicker(
    modifier: Modifier = Modifier,
    closingSheet: () -> Unit = { },
    clickAddIcon: (ImageVector) -> Unit = { },
) {

    Surface {

        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()

        val iconList = listOf<IconItem>(
            IconItem("Add", Icons.Default.Add),
            IconItem("AddCard", Icons.Default.AddCard),
            IconItem("ImageNotSupported", Icons.Default.ImageNotSupported),
            IconItem("Adjust", Icons.Default.Adjust),
            IconItem("More", Icons.Default.More),
            IconItem("Settings", Icons.Default.Settings),
            IconItem("ImageSearch", Icons.Default.ImageSearch),
            IconItem("AccountBox", Icons.Default.AccountBox),
            IconItem("AccountCircle", Icons.Default.AccountCircle),
            IconItem("MoreVert", Icons.Default.MoreVert),
        )

        ModalBottomSheet(
            modifier = modifier.fillMaxHeight(0.5f),
            sheetState = sheetState,
            onDismissRequest = {
                closingSheet.invoke()
            },
            dragHandle = null,
            containerColor = thirtyContainerDark
        ) {

            Box(
                modifier = modifier,
                contentAlignment = Alignment.BottomCenter
            ) {

                // Send icon to [CreateOwnHabitScreen]
                var pickedIcon by remember {
                    mutableStateOf(Icons.Default.Error)
                }

                var selectedItem by remember {
                    mutableStateOf<IconItem?>(null)
                }

                LazyVerticalGrid(
                    modifier = modifier.padding(bottom = 85.dp, top = 16.dp),
                    columns = GridCells.Fixed(4),
                ) {

                    items(iconList) { icon ->

                        val isSelected = icon == selectedItem

                        IconButton(
                            onClick = {
                                selectedItem = icon

                                pickedIcon = icon.image ?: Icons.Default.Error
                            },
                            modifier = modifier
                                .padding(horizontal = 18.dp)
                                .clip(RoundedCornerShape(size = 16.dp))
                                .background(
                                    if (isSelected) Color(0xFFE26A19)
                                    else Color.Transparent
                                ),

                            ) {
                            Icon(
                                imageVector = icon.image ?: Icons.Default.Error,
                                contentDescription = null,
                                modifier = modifier
                                    .size(60.dp)
                                    .padding(all = 6.dp)

                            )
                        }
                    }
                }

                Row(
                    modifier.padding(all = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    MyButton(
                        modifier = modifier.weight(1f),
                        color = thirtyContainerDark,
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
                            clickAddIcon.invoke(pickedIcon)
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
