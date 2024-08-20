package com.example.habitstracker.ui.newHabit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowCircleRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.AppTypography

@Preview(showSystemUi = true)
@Composable
fun PreviewCreateNewHabitScreen() {
    AppTheme(darkTheme = true) {
        CreateNewHabit()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewHabit() {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "Create a new habit",
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
            modifier = Modifier
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
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(0.9f)
                        .height(60.dp)
                        .border(
                            1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(corner = CornerSize(50.dp))
                        ),

                    onClick = { /*TODO*/ },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),

                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 16.dp
                    )
                ) {
                    Text(
                        text = "+ Create",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

                Text(
                    text = "or select already created",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    item{
                        DefaultHabitItem(
                            groupName = "eat",
                            groupDescribe = "Want to go on a diet and stop eating fatty foods?" +
                                    " Ready-made presets related to food can be found here",
                            icon = painterResource(R.drawable.food)
                        )
                    }

                    item{
                        DefaultHabitItem(
                            groupName = "sport",
                            groupDescribe = "Wanna looks fit? This preset help you with it",
                            icon = painterResource(id = R.drawable.baseline_sports_tennis_24)
                        )
                    }

                    item{
                        DefaultHabitItem(
                            groupName = "sport",
                            groupDescribe = "Wanna looks fit? This preset help you with it",
                            icon = painterResource(id = R.drawable.baseline_sports_tennis_24)
                        )
                    }

                    item{
                        DefaultHabitItem(
                            groupName = "sport",
                            groupDescribe = "Wanna looks fit? This preset help you with it",
                            icon = painterResource(id = R.drawable.baseline_sports_tennis_24)
                        )
                    }

                    item{
                        DefaultHabitItem(
                            groupName = "sport",
                            groupDescribe = "Wanna looks fit? This preset help you with it",
                            icon = painterResource(id = R.drawable.baseline_sports_tennis_24)
                        )
                    }

                    item{
                        DefaultHabitItem(
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