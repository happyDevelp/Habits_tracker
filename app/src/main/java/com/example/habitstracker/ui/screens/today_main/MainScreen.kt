package com.example.habitstracker.ui.screens.today_main

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
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.data.db.HabitEntity
import com.example.habitstracker.data.db.viewmodel.HabitViewModel
import com.example.habitstracker.navigation.RoutesMainScreen
import com.example.habitstracker.ui.custom.CustomRippleTheme
import com.example.habitstracker.ui.screens.today_main.components.HabitItem
import com.example.habitstracker.ui.screens.today_main.components.calendar.CalendarRowList
import com.example.habitstracker.ui.screens.today_main.scaffold.TopBarMainScreen
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import com.example.habitstracker.ui.theme.blueColor
import com.example.habitstracker.ui.theme.screenContainerBackgroundDark
import com.example.habitstracker.ui.theme.screensBackgroundDark
import com.example.habitstracker.utils.CREATE_NEW_HABIT_BUTTON
import com.example.habitstracker.utils.generateDateSequence
import com.example.habitstracker.utils.toHex
import java.time.LocalDate


@Composable
fun TodayScreenContent(
    modifier: Modifier = Modifier,
    habitLIstState: List<HabitEntity>,
    onSelectedStateUpdate: (id: Int, isDone: Boolean) -> Unit,
) {
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
            shape = RoundedCornerShape(topStart = 27.dp, topEnd = 27.dp)
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

                        items(habitLIstState.size) { habit ->
                            HabitItem(
                                habit = habitLIstState[habit],
                                onUpdateSelectedState = onSelectedStateUpdate
                            )
                            Spacer(modifier = modifier.height(20.dp))
                        }

                        item {
                            Spacer(modifier = modifier.height(6.dp))

                            Button(
                                modifier = modifier
                                    .padding(bottom = 20.dp)
                                    .fillMaxWidth(0.7f)
                                    .height(50.dp)
                                    .testTag(CREATE_NEW_HABIT_BUTTON),

                                onClick = {
                                    navController.navigate(RoutesMainScreen.AddHabit.route)
                                },

                                colors = ButtonDefaults.buttonColors(
                                    containerColor = screenContainerBackgroundDark,
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

fun getIconName(icon: ImageVector): String {
    return icon.name.split(".")[1]
}

fun iconByName(name: String): ImageVector {
    val cl = Class.forName("androidx.compose.material.icons.filled.${name}Kt")
    val method = cl.declaredMethods.first()
    return method.invoke(null, Icons.Filled) as ImageVector
}

@Composable
@Preview(showSystemUi = false)
private fun Preview() {
    val mockNavController = rememberNavController()
    val mockList = listOf(
        HabitEntity(
            0, "habit example", "SentimentVerySatisfied", false,
            blueColor.toHex(), "Everyday", "Anytime", false,
        )
    )

    val mockSelectedStateEvent = { id: Int, isDone: Boolean -> }

    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            TodayScreenContent(
                habitLIstState = mockList,
                onSelectedStateUpdate = mockSelectedStateEvent
            )
        }
    }
}

@Composable
fun TodayScreen(_viewModel: HabitViewModel? = null) {
    val viewModel = if(_viewModel != null) _viewModel
    else hiltViewModel<HabitViewModel>()

    val _stateList = viewModel.habitsList.collectAsState()
    val _onSelectedStateUpdate = { id: Int, isDone: Boolean ->
        viewModel.updateSelectedState(id, isDone)
    }
    TodayScreenContent(
        habitLIstState = _stateList.value,
        onSelectedStateUpdate = _onSelectedStateUpdate
    )
}