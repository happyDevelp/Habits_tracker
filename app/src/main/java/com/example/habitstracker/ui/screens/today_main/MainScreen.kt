package com.example.habitstracker.ui.screens.today_main

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
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.navigation.RoutesMainScreen
import com.example.habitstracker.ui.custom.CustomRippleTheme
import com.example.habitstracker.ui.screens.today_main.components.calendar.CalendarRowList
import com.example.habitstracker.ui.screens.today_main.components.HabitItem
import com.example.habitstracker.ui.screens.today_main.scaffold.TopBarMainScreen
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import com.example.habitstracker.ui.theme.screensBackgroundDark
import com.example.habitstracker.ui.theme.thirtyContainerDark
import com.example.habitstracker.utils.generateDateSequence
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showSystemUi = false)
private fun Preview() {
    AppTheme(darkTheme = true) {
        val mockNavController = rememberNavController()
        CompositionLocalProvider(value = LocalNavController provides mockNavController) {
            TodayScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayScreen(modifier: Modifier = Modifier) {

    val navController = LocalNavController.current

    Scaffold(
        topBar = { TopBarMainScreen(modifier, navController) },
    ) { paddingValues ->

        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),

            colors = CardDefaults.cardColors(
                containerColor = screensBackgroundDark,
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
                            Spacer(modifier = modifier.height(6.dp))

                            Button(
                                modifier = modifier
                                    .padding(bottom = 20.dp)
                                    .fillMaxWidth(0.7f)
                                    .height(50.dp),

                                onClick = {
                                    navController.navigate(RoutesMainScreen.AddHabit.route)
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
                                    color = Color.White,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}