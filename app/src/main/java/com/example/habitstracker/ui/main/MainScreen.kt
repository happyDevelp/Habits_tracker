package com.example.habitstracker.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.ui.custom.CustomRippleTheme
import com.example.habitstracker.ui.main.Calendar.CalendarRowList
import com.example.habitstracker.ui.newHabit.DefaultHabitItem
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.AppTypography
import com.example.habitstracker.ui.theme.buttonAddNewHabit
import com.example.habitstracker.utils.generateDateSequence
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showSystemUi = true)
fun PreviewMainScreen() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        MainScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
        MyScaffold()
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold() {

    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(top = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "Today",
                                fontSize = AppTypography.headlineMedium.fontSize,
                                style = MaterialTheme.typography.titleSmall
                            )

                            val today = LocalDate.now()
                            val formatter = DateTimeFormatter.ofPattern("MMMM")
                            val month = today.format(formatter)
                            Text(
                                text = "${today.dayOfMonth} $month",
                                fontSize = 12.sp,
                            )
                        }
                    }
                },

                navigationIcon = {
                    /*IconButton(onClick = { *//*TODO*//* }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_habit)
                        )
                    }*/
                },

                actions = {
                    IconButton(
                        onClick = { navController.navigate("CreateNewHabit") },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_habit),
                        )
                    }
                },

                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            )
        },

        bottomBar = {
            BottomAppBar {
                NavigationBarItem(

                    selected = true,

                    onClick = { /*TODO*/ },

                    icon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = stringResource(R.string.today)
                        )
                    },

                    label = { Text(text = stringResource(R.string.today)) }
                )


                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = stringResource(R.string.today)
                        )
                    },

                    label = { Text(text = stringResource(R.string.history)) }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = stringResource(R.string.today)
                        )
                    },

                    label = { Text(text = stringResource(R.string.me)) }
                )

            }
        }


    ) { paddingValues ->

        val context = LocalContext.current

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),

            shape = RoundedCornerShape(
                topStart = 25.dp,
                topEnd = 25.dp,
            )
        )
        {

            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter

            ) {

                Column {
                    val dateSet = generateDateSequence(LocalDate.now(), 500)
                    CalendarRowList(dateSet.toMutableList())
                }

                CompositionLocalProvider(
                    value = LocalRippleTheme provides CustomRippleTheme(color = Color.Black)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 95.dp)
                            .fillMaxHeight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(4) {
                            HabitItem()
                        }

                        item {

                            Spacer(modifier = Modifier.padding(bottom = 30.dp))

                            Button(
                                modifier = Modifier
                                    .padding(bottom = 20.dp)
                                    .fillMaxWidth(0.7f)
                                    .height(50.dp),
                                onClick = {
                                    navController.navigate("CreateNewHabit")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonAddNewHabit,
                                    contentColor = Color.White.copy(alpha = 0.75f)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 16.dp
                                ),
                                //interactionSource =
                            ) {
                                Text(
                                    text = stringResource(R.string.create_a_new_habit),
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}