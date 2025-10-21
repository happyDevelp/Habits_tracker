package com.example.habitstracker.habit.presentation.add_habit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.navigation.Route
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.MyPalette
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.core.presentation.utils.TestTags
import com.example.habitstracker.habit.presentation.add_habit.components.DefaultHabitGroupItem
import com.example.habitstracker.habit.presentation.add_habit.components.defaultsHabitsGroupList

@Composable
fun AddHabitScreen(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = { TopBarAddHabitScreen(navController) },
        containerColor = screenBackgroundDark,
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    modifier = modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                        .border(
                            1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(corner = CornerSize(50.dp))
                        )
                        .testTag(TestTags.CREATE_OWN_HABIT_BUTTON)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(Color(0xFF6CD1F5), Color(0xFF3176FF))
                            ),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    onClick = {
                        navController.navigate(Route.CreateHabit(name = null, icon = null, iconColor = null))
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                        //Color(0xFF00C9A7)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 16.dp
                    )
                ) {
                    Text(
                        text = stringResource(R.string.create_your_own),
                        fontSize = 15.sp,
                        color = Color.White,
                        fontFamily = PoppinsFontFamily
                    )
                }
                Text(
                    text = stringResource(R.string.or_choose_from_presents),
                    fontSize = 12.sp,
                    modifier = modifier.padding(top = 16.dp),
                    fontFamily = PoppinsFontFamily,
                    color = Color.White.copy(0.85f)
                )
                Spacer(modifier = modifier.height(20.dp))

                LazyColumn(modifier = modifier.fillMaxHeight()) {
                    items(defaultsHabitsGroupList) { groupItem ->
                        DefaultHabitGroupItem(groupItem)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAddHabitScreen(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "New Habit",
                fontSize = 20.sp,
                color = Color.White,
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
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back button"
                )
            }
        }
    )
}

@Composable
@Preview(showSystemUi = false)
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) { AddHabitScreen() }
    }
}