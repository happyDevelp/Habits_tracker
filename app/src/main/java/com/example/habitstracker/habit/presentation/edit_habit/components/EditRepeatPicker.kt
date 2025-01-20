package com.example.habitstracker.habit.presentation.edit_habit.components

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.core.presentation.MyButton
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.presentation.today_main.MainScreenViewModel
import com.example.habitstracker.utils.clickWithRipple
import com.example.habitstracker.utils.getCorrectSelectedDaysList
import com.example.habitstracker.utils.habitEntityExample
import com.example.habitstracker.utils.textState
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Composable
fun EditRepeatPickerRoot(paramId: Int) {
    val viewModel = hiltViewModel<MainScreenViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val allHabits = viewModel.habitsList.collectAsStateWithLifecycle()
    val currentHabit = allHabits.value.find { it.id == paramId }
        ?: throw Exception("Current habit is null. (EditRepeatPickerScreen)")

    val onSaveClick: (days: String) -> Unit = { days ->
        coroutineScope.launch {
            viewModel.updateHabit(
                currentHabit.copy(days = days)
            )
        }
    }

    EditRepeatPickerScreen(
        habit = currentHabit,
        onSaveClick = onSaveClick,
    )
}

@Composable
fun EditRepeatPickerScreen(
    habit: HabitEntity,
    modifier: Modifier = Modifier,
    onSaveClick: (days: String) -> Unit,
) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = { RepeatPickerTopBar(navController, modifier) },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) { paddingValues ->


        val dayStatesList = getCorrectSelectedDaysList(habit.days)
        // list to observing the states of each day choose
        val dayStates = remember {
            dayStatesList.toMutableStateList()
        }


        val selectedDayText = textState(dayStates)

        ConstraintLayout(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            val firstOption = stringResource(R.string.firstOption_repeatPicer)
            val secondOption = stringResource(R.string.secondOption_repeatPicker)


            var currentOptionSelected by remember {
                mutableStateOf(firstOption)
            }

            /** First clickable item  */

            val (
                certainWeekDaysShort,
                certainWeekDaysFull,
            ) = createRefs()

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .height(50.dp)
                    .clip(
                        if (currentOptionSelected == firstOption)
                            RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        else RoundedCornerShape(size = 8.dp)
                    )
                    .background(screenContainerBackgroundDark)
                    .clickWithRipple { currentOptionSelected = firstOption }
                    .constrainAs(certainWeekDaysShort) {
                        top.linkTo(parent.top)
                    },

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MyText(
                        text = stringResource(R.string.define_habit_days),
                        textSize = 15.sp,
                    )

                    Card(
                        modifier = modifier
                            .size(22.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = if (currentOptionSelected == secondOption) Color.Transparent
                            else Color(0xFF08A712)
                        ),

                        border = if (currentOptionSelected == secondOption)
                            BorderStroke(2.dp, Color.LightGray.copy(alpha = 0.70f))
                        else
                            BorderStroke(0.dp, Color.Transparent),

                        shape = RoundedCornerShape(20.dp),
                    ) {

                        Icon(
                            modifier = modifier.padding(2.dp),
                            imageVector = Icons.Default.Check,
                            contentDescription = "Task is done button",
                            tint = if (currentOptionSelected == firstOption) Color.White
                            else Color.Transparent
                        )
                    }
                }
            }

            if (currentOptionSelected == firstOption) {
                Column(
                    modifier = modifier
                        .height(105.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                        .background(screenContainerBackgroundDark)
                        .constrainAs(certainWeekDaysFull) {
                            top.linkTo(certainWeekDaysShort.bottom)
                        },
                ) {
                    MyText(
                        modifier = modifier.padding(top = 8.dp, start = 12.dp, bottom = 16.dp),
                        text = selectedDayText.first,
                        textSize = 13.sp,
                        color = Color.White.copy(0.7f)
                    )

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        DayOfWeekItems(dayStates)
                    }
                }
            }



            /** Second clickable item  */

            val (
                dayCountsInAWeekShort,
                dayCountsInAWeekFull,
            ) = createRefs()

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .height(50.dp)
                    .clip(
                        if (currentOptionSelected == firstOption)
                            RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        else RoundedCornerShape(size = 8.dp)
                    )
                    .background(screenContainerBackgroundDark)
                    .clickWithRipple { currentOptionSelected = secondOption }

                    .constrainAs(dayCountsInAWeekShort) {
                        if (currentOptionSelected == firstOption)
                            top.linkTo(certainWeekDaysFull.bottom)
                        else
                            top.linkTo(certainWeekDaysShort.bottom)
                    },

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MyText(
                        text = stringResource(R.string.quantity_per_week),
                        textSize = 15.sp,
                    )

                    Card(
                        modifier = modifier
                            .size(22.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = if (currentOptionSelected == firstOption) Color.Transparent
                            else Color(0xFF08A712)
                        ),

                        border = if (currentOptionSelected == firstOption)
                            BorderStroke(2.dp, Color.LightGray.copy(alpha = 0.70f))
                        else
                            BorderStroke(0.dp, Color.Transparent),

                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Icon(
                            modifier = modifier.padding(2.dp),
                            imageVector = Icons.Default.Check,
                            contentDescription = "Task is done button",
                            tint = if (currentOptionSelected == secondOption) Color.White
                            else Color.Transparent
                        )
                    }
                }
            }

            if (currentOptionSelected == secondOption) {
                Column(
                    modifier = modifier
                        .height(105.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                        .background(screenContainerBackgroundDark)
                        .constrainAs(dayCountsInAWeekFull) {
                            top.linkTo(dayCountsInAWeekShort.bottom)
                        },
                ) {

                    MyText(
                        modifier = modifier.padding(top = 8.dp, start = 12.dp, bottom = 16.dp),
                        text = stringResource(R.string.how_often_per_week),
                        textSize = 13.sp,
                        color = Color.White.copy(0.7f)
                    )

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) { QuantityPerWeekItems() }
                }
            }

            val (
                divider,
                infoColumn,
                saveButton,
            ) = createRefs()

            HorizontalDivider(
                modifier = modifier.constrainAs(divider) {
                    top.linkTo(
                        if (currentOptionSelected == firstOption)
                            dayCountsInAWeekShort.bottom
                        else dayCountsInAWeekFull.bottom,
                        margin = 24.dp
                    )
                },
                thickness = 0.3.dp,
                color = Color.White
            )

            Column(
                modifier = modifier
                    .padding(start = 16.dp, end = 28.dp)
                    .constrainAs(infoColumn) {
                        top.linkTo(divider.bottom, margin = 24.dp)
                    }
            ) {
                MyText(
                    modifier.padding(bottom = 6.dp),
                    text = "Long-term habits",
                    textSize = 14.sp
                )

                Text(
                    modifier = modifier.padding(end = 18.dp),
                    text = buildAnnotatedString {
                        append("In your habits list, the long-term habit will be displayed separately in the ")
                        withStyle(style = SpanStyle(color = Color(0xFFFFA500))) {
                            append("\"Long-term\"")
                        }
                        append(" category.")
                    },
                    fontSize = 12.sp,
                    color = Color.White.copy(0.7f)
                )
            }


            MyButton(
                modifier = modifier
                    .fillMaxWidth(0.85f)
                    .constrainAs(saveButton) {
                        bottom.linkTo(parent.bottom, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },

                onClick = {
                    onSaveClick(selectedDayText.second)
                    navController.navigateUp()
                }
            ) {
                MyText(
                    text = stringResource(R.string.save),
                    textSize = 17.sp
                )
            }
        }
    }
}

@Composable
private fun QuantityPerWeekItems() {

    var currentSelectedDay by remember {
        mutableStateOf("7")
    }
    listOf("1", "2", "3", "4", "5", "6", "7")
        .forEach { num ->

            QuantityDaysItem(
                text = num,
                isSelected = currentSelectedDay == num,
                onQuantityClick = { currentSelectedDay = num }
            )
        }
}

@Composable
private fun DayOfWeekItems(dayStates: SnapshotStateList<SelectedDay>) {
    listOf("Mon", "Tue", "Wed", "Thu", "Fri", "San", "Sut")
        .forEachIndexed { index, day ->

            DaysItem(
                text = day,
                isSelected = dayStates[index].isSelect,
                onDayClick = { isSelected ->
                    // isSelected param mean when I click from false to true
                    if (isSelected || dayStates.count { it.isSelect } > 1)
                        dayStates[index] =
                            dayStates[index].copy(isSelect = isSelected)
                }
            )
        }
}

@Composable
fun DaysItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onDayClick: (Boolean) -> Unit,
) {

    Box(
        modifier = modifier
            .size(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else Color(0xFF404B53)
            )
            .clickWithRipple {
                onDayClick.invoke(!isSelected)
            },
        contentAlignment = Alignment.Center
    ) {

        MyText(
            text = text,
            textSize = 13.5.sp,
            color = Color.White.copy(0.90f)
        )
    }
}

@Composable
private fun QuantityDaysItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onQuantityClick: () -> Unit,
) {

    Box(
        modifier = modifier
            .size(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else Color(0xFF404B53)
            )
            .clickWithRipple {
                onQuantityClick.invoke()
            },
        contentAlignment = Alignment.Center
    ) {

        MyText(
            text = text,
            textSize = 13.5.sp,
            color = Color.White.copy(0.90f)
        )
    }
}

@Parcelize
data class SelectedDay(
    var isSelect: Boolean,
    val day: String,
) : Parcelable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun RepeatPickerTopBar(
    navController: NavHostController,
    modifier: Modifier,
) {
    TopAppBar(
        title = {
            MyText(
                text = "Edit Days of Habits",
                color = Color.White.copy(alpha = 1f),
            )
        },

        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Cancel",
                    modifier = modifier.size(26.dp)
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
    )
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            EditRepeatPickerScreen(
                onSaveClick = {},
                habit = habitEntityExample
            )
        }
    }
}