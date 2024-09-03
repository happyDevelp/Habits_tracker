package com.example.habitstracker.ui.addHabit

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.ui.main.LocalNavController
import com.example.habitstracker.ui.theme.AppTheme

@Composable
@Preview(showSystemUi = false)
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            AddHabitScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "Add a new habit",
                        fontSize = 24.sp,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        },

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

                val create_own_habit_navigation =
                    stringResource(id = R.string.create_own_habit_navigation)

                Button(
                    modifier = modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(0.9f)
                        .height(60.dp)
                        .border(
                            1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(corner = CornerSize(50.dp))
                        ),

                    onClick = {
                        navController.navigate(create_own_habit_navigation)
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),

                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 16.dp
                    )
                ) {
                    Text(
                        text = stringResource(R.string.create_button),
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