package com.example.habitstracker.habit.presentation.detail_habit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.habit.presentation.detail_habit.components.DefaultHabitDetailItem
import com.example.habitstracker.habit.presentation.detail_habit.components.getGroupDetails

@Composable
fun DetailHabitScreen(
    modifier: Modifier = Modifier,
    groupName: String = "Keep active & get fit",
    groupDescribe: String = "Sweat never lies"
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val groupItems = getGroupDetails(groupName, context = context)
    Scaffold(topBar = { CustomTopBar() }) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
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
                        fontSize = 20.sp,
                        fontFamily = PoppinsFontFamily
                    )
                    Spacer(modifier.height(8.dp))
                    Text(
                        text = groupDescribe,
                        fontSize = 14.sp,
                        color = Color.White.copy(0.9f)
                    )
                }
                Spacer(modifier.height(10.dp))

                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(screenContainerBackgroundDark)

                ) {
                    groupItems.forEach { detailItem ->
                        item { DefaultHabitDetailItem(detailItem) }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CustomTopBar() {
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        navigationIcon = {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
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
        AppTheme(darkTheme = true) { DetailHabitScreen() }
    }
}