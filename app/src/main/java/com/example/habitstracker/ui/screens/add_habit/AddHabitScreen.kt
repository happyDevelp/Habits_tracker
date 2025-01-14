package com.example.habitstracker.ui.screens.add_habit

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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.navigation.Route
import com.example.habitstracker.navigation.RoutesMainScreen
import com.example.habitstracker.ui.screens.add_habit.components.DefaultChooseHabitItem
import com.example.habitstracker.ui.screens.add_habit.scaffold.TopBarAddHabitScreen
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.blueColor
import com.example.habitstracker.utils.TestTags

@Composable
@Preview(showSystemUi = false)
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) { AddHabitScreen() }
    }
}

@Composable
fun AddHabitScreen(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = { TopBarAddHabitScreen() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
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
                Text(
                    text = "Here you can create a new habit",
                    fontSize = 20.sp,
                    color = Color.White
                )

                Button(
                    modifier = modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(0.9f)
                        .height(60.dp)
                        .border(
                            1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(corner = CornerSize(50.dp))
                        )
                        .testTag(TestTags.CREATE_OWN_HABIT_BUTTON),

                    onClick = {
                        navController.navigate(Route.CreateHabit(param = null))
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueColor
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 16.dp
                    )
                ) {
                    Text(
                        text = stringResource(R.string.create_your_own),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

                Text(
                    text = "or select already created",
                    fontSize = 15.sp,
                    modifier = modifier.padding(top = 16.dp)
                )

                Spacer(modifier = modifier.height(20.dp))

                LazyColumn(
                    modifier = modifier.fillMaxHeight()
                ) {
                    item {
                        DefaultChooseHabitItem(
                            groupName = "eat",
                            groupDescribe = "Want to go on a diet and stop eating fatty foods?" +
                                    " Ready-made presets related to food can be found here",
                            icon = painterResource(R.drawable.food)
                        )
                    }

                    item {
                        DefaultChooseHabitItem(
                            groupName = "sport",
                            groupDescribe = "Wanna looks fit? This preset help you with it",
                            icon = painterResource(id = R.drawable.baseline_sports_tennis_24)
                        )
                    }

                    item {
                        DefaultChooseHabitItem(
                            groupName = "sport",
                            groupDescribe = "Wanna looks fit? This preset help you with it",
                            icon = painterResource(id = R.drawable.baseline_sports_tennis_24)
                        )
                    }

                    item {
                        DefaultChooseHabitItem(
                            groupName = "sport",
                            groupDescribe = "Wanna looks fit? This preset help you with it",
                            icon = painterResource(id = R.drawable.baseline_sports_tennis_24)
                        )
                    }

                    item {
                        DefaultChooseHabitItem(
                            groupName = "sport",
                            groupDescribe = "Wanna looks fit? This preset help you with it",
                            icon = painterResource(id = R.drawable.baseline_sports_tennis_24)
                        )
                    }

                    item {
                        DefaultChooseHabitItem(
                            groupName = "sport",
                            groupDescribe = "Wanna looks fit? This preset help you with it",
                            icon = painterResource(id = R.drawable.baseline_sports_tennis_24)
                        )
                    }
                }
            }
        }
    }
}