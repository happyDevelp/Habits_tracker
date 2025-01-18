package com.example.habitstracker.navigation.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.habitstracker.ui.theme.PoppinsFontFamily

@Composable
fun NavigationBottomBar(
    navController: NavHostController,
) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    NavigationBar {
        listOfNavItems.forEachIndexed() { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,

                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex)
                            item.selectedIcon
                        else item.unSelectedIcon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White.copy(0.7f)
                    )
                }
            )
        }
    }
}