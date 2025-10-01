package com.example.habitstracker.app.navigation.bottombar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily

@Composable
fun NavigationBottomBar(
    navController: NavHostController,
    selectedItemIndex: Int,
    changeSelectedItemState: (index: Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = Color(0xFF363641)
    ) {
        listOfNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    changeSelectedItemState(index)
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
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}