package com.example.habitstracker.habit.presentation.create_own_habit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.habitstracker.core.presentation.MyButton
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    AppTheme(darkTheme = true) {
        IconPicker(
            onAddIconClick = {},
            onCloseClick = {},
            color = Color(0xFFE26A19)
        )
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
    color: Color = Color(0xFFE26A19),
    onAddIconClick: (ImageVector) -> Unit = { },
    onCloseClick: () -> Unit
) {
    val tabItems = listOf(
        "All",
        "Popular",
        "Lifestyle",
        "Health",
        "Diet"
    )

    Surface {
        val coroutineScope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) { tabItems.size }

        val iconList = listOf(
            all,
            popular,
            lifestyle,
            health,
            diet
        )

        val selectedItems = remember {
            mutableStateMapOf<Int, IconItem?>() // Key - tab index, value - selected icon
        }

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onCloseClick.invoke()
                coroutineScope.launch { sheetState.hide() }
            },
            dragHandle = null,
            containerColor = containerBackgroundDark,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 0.dp,
                    indicator = { },
                ) {
                    tabItems.forEachIndexed { index, title ->
                        val interactionSource = remember { MutableInteractionSource() }
                        val isSelected = pagerState.currentPage == index
                        Tab(
                            modifier = Modifier
                                .height(50.dp)
                                .padding(horizontal = 4.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    color = if (isSelected)
                                        Color(0xFF3B04BD) else Color.Transparent
                                )
                                .selectable(
                                    selected = isSelected,
                                    onClick = { /* handle tab click */ },
                                    interactionSource = interactionSource,
                                    indication = ripple()
                                ),
                            selectedContentColor = Color(0xFF3B04BD),
                            unselectedContentColor = Color.Transparent,
                            selected = isSelected,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = title,
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    color = if (isSelected) Color.White else Color.Gray
                                )
                            },
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val grids = tabItems.mapIndexed { index, _ ->
                        remember {
                            iconList[index]
                        }
                    }

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 80.dp),
                    ) { page ->
                        LazyVerticalGrid(
                            modifier = Modifier
                                .padding(top = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            columns = GridCells.Fixed(4),
                        ) {
                            items(grids[page], key = { it.name }) { item ->
                                IconButton(
                                    onClick = { selectedItems[page] = item },
                                    modifier = modifier
                                        .padding(horizontal = 18.dp)
                                        .clip(RoundedCornerShape(size = 16.dp))
                                        .background(
                                            if (item == selectedItems[page]) color
                                            else Color.Transparent
                                        )
                                ) {
                                    Icon(
                                        imageVector = item.image ?: Icons.Default.Error,
                                        contentDescription = null,
                                        modifier = modifier
                                            .size(60.dp)
                                            .padding(all = 6.dp)
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier
                            .padding(all = 10.dp)
                            .padding(bottom = 16.dp)
                            .align(Alignment.BottomEnd),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MyButton(
                            modifier = modifier.weight(1f),
                            color = containerBackgroundDark,
                            onClick = {
                                onCloseClick.invoke()
                                coroutineScope.launch { sheetState.hide() }
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
                                val icon = selectedItems[pagerState.currentPage]?.image
                                if (icon != null) {
                                    onAddIconClick(icon)
                                }
                                onCloseClick.invoke()
                                coroutineScope.launch { sheetState.hide() }
                            },
                        ) {
                            Text(
                                text = stringResource(R.string.add),
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}