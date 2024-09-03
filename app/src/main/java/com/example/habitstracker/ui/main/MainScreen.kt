package com.example.habitstracker.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.ui.custom.CustomRippleTheme
import com.example.habitstracker.ui.main.Calendar.CalendarRowList
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.AppTypography
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import com.example.habitstracker.ui.theme.surfaceDark
import com.example.habitstracker.ui.theme.thirtyContainerDark
import com.example.habitstracker.utils.generateDateSequence
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showSystemUi = false)
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {

            MainScreen()

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.padding(vertical = 5.dp),

                title = {

                    Box(
                        modifier = modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        Column(
                            modifier = modifier
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

                // FloatButton
                actions = {
                    val add_habit_navigation =
                        stringResource(id = R.string.add_habit_screen_navigation)

                    IconButton(
                        onClick = {
                            navController.navigate(add_habit_navigation)
                        },

                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = Color.White
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_habit),
                        )
                    }
                },
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

        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),

            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),

            shape = RoundedCornerShape(
                topStart = 27.dp,
                topEnd = 27.dp,
            )
        )
        {

            Box(
                modifier = modifier
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
                        modifier = modifier
                            .padding(top = 95.dp)
                            .fillMaxHeight(1f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(4) {
                            HabitItem()
                            Spacer(modifier = modifier.height(20.dp))
                        }

                        item {
                            val add_habit_navigation =
                                stringResource(id = R.string.add_habit_screen_navigation)

                            Spacer(modifier = modifier.height(6.dp))

                            Button(
                                modifier = modifier
                                    .padding(bottom = 20.dp)
                                    .fillMaxWidth(0.7f)
                                    .height(50.dp),

                                onClick = {
                                    navController.navigate(add_habit_navigation)
                                },

                                colors = ButtonDefaults.buttonColors(
                                    containerColor = thirtyContainerDark,
                                    contentColor = Color.White.copy(alpha = 0.75f)
                                ),

                                shape = RoundedCornerShape(10.dp),

                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 16.dp
                                ),

                                ) {
                                Text(
                                    text = stringResource(R.string.create_a_new_habit),
                                    modifier = modifier.padding(horizontal = 8.dp),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = PoppinsFontFamily,
                                    color = Color.White.copy(alpha = 0.9f),
                                )
                            }
                        }


                    }
                }
            }
        }
    }
}