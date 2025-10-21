package com.example.habitstracker.habit.presentation.detail_habit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.habit.presentation.detail_habit.components.DefaultHabitDetailItem
import com.example.habitstracker.habit.presentation.detail_habit.components.getGroupDetails
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark

@Composable
fun DetailHabitScreen(
    modifier: Modifier = Modifier,
    groupName: String,
    groupDescribe: String
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val groupItems = getGroupDetails(groupName, context = context)
    Scaffold(topBar = { CustomTopBar(navController) }) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(screenBackgroundDark),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = modifier.padding(),
                        text = groupName,
                        fontSize = 22.sp,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White
                    )
                    Text(
                        text = groupDescribe,
                        fontSize = 15.sp,
                        color = Color.White.copy(0.9f)
                    )
                }
                Spacer(modifier.height(4.dp))
                LazyVerticalGrid(
                    modifier = modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 12.dp
                            )
                        )
                        .padding(horizontal = 8.dp)
                        .background(screenBackgroundDark),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(groupItems) { detailedItem ->
                        DefaultHabitDetailItem(detailedItem)
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CustomTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Choose a habit",
                fontSize = 20.sp,
                color = Color.White.copy(0.85f),
                fontFamily = PoppinsFontFamily
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screenBackgroundDark
        ),
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() },
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Go back button"
                )
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            DetailHabitScreen(
                groupName = UiText.StringResources(R.string.get_fit).asString(LocalContext.current),
                groupDescribe = "Sweat never lies"
            )
        }
    }
}