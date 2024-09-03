package com.example.habitstracker.ui.сreateOwnHabit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.ui.main.LocalNavController
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import com.example.habitstracker.ui.theme.thirtyContainerDark
import com.example.habitstracker.utils.clickWithRipple

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            CreateOwnHabitScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOwnHabitScreen(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            TopAppBar(

                title = { Text(text = "") },

                navigationIcon = {
                    val add_habit_screen_navigation =
                        stringResource(R.string.add_habit_screen_navigation)
                    IconButton(
                        onClick = { navController.navigate(add_habit_screen_navigation) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "Cancel",
                            modifier = modifier.size(26.dp)
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )

        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer


    ) { paddingValues ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {

                var currentText by remember {
                    mutableStateOf("")
                }

                TextField(
                    modifier = modifier.fillMaxWidth(),
                    value = currentText,
                    onValueChange = { newText ->
                        if (newText.length < 30)
                            currentText = newText
                    },

                    textStyle = TextStyle(
                        color = Color.White.copy(alpha = 0.85f),
                        fontFamily = PoppinsFontFamily,
                        fontSize = 22.sp,
                    ),

                    placeholder = {
                        Text(
                            text = "Name of habit",
                            color = Color.White.copy(alpha = 0.85f),
                            fontFamily = PoppinsFontFamily,
                            fontSize = 22.sp,
                        )
                    },

                    trailingIcon = {
                        Icon(
                            modifier = Modifier.padding(end = 0.dp),
                            imageVector = Icons.Default.Edit, contentDescription = null
                        )
                    },

                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        focusedIndicatorColor = Color.Transparent,
                    ),

                    singleLine = true,
                )

                Spacer(modifier = modifier.height(16.dp))

                var currentColor: Color by remember {
                    mutableStateOf(Color(0xFFE26A19))
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(color = thirtyContainerDark),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    var currentIcon by remember {
                        mutableStateOf(Icons.Filled.SentimentVerySatisfied)
                    }

                    IconButton(
                        modifier = modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.18f),
                        onClick = { /*TODO*/ },
                    ) {

                        Icon(
                            modifier = modifier.size(32.dp),
                            imageVector = currentIcon,
                            contentDescription = "Select icon",
                            tint = currentColor
                        )
                    }

                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {

                        var isIconSheetOpen by remember {
                            mutableStateOf(false)
                        }

                        if (isIconSheetOpen) {
                            IconPicker(
                                closingSheet = { isIconSheetOpen = false },
                                clickAddIcon = { selectedIcon ->
                                    currentIcon = selectedIcon
                                }
                            )
                        }
                        Box(
                            modifier = modifier
                                .fillMaxSize()
                                .weight(1f)
                                .clickWithRipple(color = Color.White) {
                                    isIconSheetOpen = true
                                },

                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Text(
                                modifier = modifier.padding(start = 8.dp),
                                text = "Icon",
                                fontFamily = PoppinsFontFamily,
                                color = Color.White,
                            )

                            Icon(
                                modifier = modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(12.dp),
                                imageVector = Icons.Default.ChevronRight,
                                tint = Color.White.copy(0.70f),
                                contentDescription = null
                            )
                        }

                        HorizontalDivider(
                            thickness = 0.25.dp,
                            color = Color.White
                        )


                        var isColorSheetOpen by remember {
                            mutableStateOf(false)
                        }
                        if (isColorSheetOpen) {
                            ColorPicker(
                                closingSheet = { isColorSheetOpen = false },
                                clickAddColor = { selectedColor ->
                                    currentColor = selectedColor
                                }
                            )
                        }
                        Box(
                            modifier = modifier
                                .fillMaxSize()
                                .weight(1f)
                                .clickWithRipple(color = Color.White) {
                                    isColorSheetOpen = true
                                },
                            contentAlignment = Alignment.CenterStart
                        ) {

                            Text(
                                modifier = modifier.padding(start = 8.dp),

                                text = "Color",
                                fontFamily = PoppinsFontFamily,
                                color = Color.White,
                            )

                            Row(
                                modifier = modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = modifier
                                        .size(14.dp)
                                        .clip(RoundedCornerShape(50.dp))
                                        .background(currentColor)
                                        .width(150.dp)
                                )

                                Spacer(modifier = modifier.width(14.dp))

                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    tint = Color.White.copy(0.70f),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = modifier.height(12.dp))

                Text(
                    text = "REPEAT",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.50f),
                )

                Spacer(modifier = modifier.height(12.dp))

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(color = thirtyContainerDark)
                        .clickWithRipple(
                            color = Color.White
                        ) { /*TODO*/ },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = modifier.padding(start = 16.dp),
                        text = "Days of habits",
                        fontFamily = PoppinsFontFamily,
                        fontSize = 14.sp,
                        color = Color.White,
                    )

                    Row(
                        modifier = modifier.padding(end = 12.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Mon, Tue. Tue...",
                            fontFamily = PoppinsFontFamily,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.5f),
                        )

                        Spacer(modifier = modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White.copy(0.7f)
                        )
                    }
                }

                Spacer(modifier = modifier.height(12.dp))

                Text(
                    text = "Executing time",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.50f),
                )

                Spacer(modifier = modifier.height(12.dp))

                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(125.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(color = Color.Transparent),
                ) {
                    val doesntMatterText = stringResource(id = R.string.doesnt_matter_create_habit)
                    val morningText = stringResource(id = R.string.morning_create_habit)
                    val dayText = stringResource(id = R.string.day_create_habit)
                    val eveningText = stringResource(id = R.string.evening_create_habit)

                    var selectedOption by remember {
                        mutableStateOf(doesntMatterText)
                    }

                    ExecutionTimeItem(
                        modifier.align(Alignment.TopStart),
                        text = stringResource(R.string.doesnt_matter_create_habit),
                        isSelected = selectedOption == doesntMatterText,
                        onClick = { selectedOption = doesntMatterText }
                    )

                    ExecutionTimeItem(
                        modifier = modifier.align(Alignment.TopEnd),
                        text = stringResource(R.string.morning_create_habit),
                        icon = Icons.Outlined.WbTwilight,
                        isSelected = selectedOption == morningText,
                        onClick = { selectedOption = morningText }
                    )

                    ExecutionTimeItem(
                        modifier = modifier.align(Alignment.BottomStart),
                        text = stringResource(R.string.day_create_habit),
                        icon = Icons.Outlined.WbSunny,
                        isSelected = selectedOption == dayText,
                        onClick = { selectedOption = dayText }
                    )

                    ExecutionTimeItem(
                        modifier = modifier.align(Alignment.BottomEnd),
                        text = stringResource(R.string.evening_create_habit),
                        icon = Icons.Outlined.DarkMode,
                        isSelected = selectedOption == eveningText,
                        onClick = { selectedOption = eveningText }
                    )
                }

                Spacer(modifier = modifier.height(16.dp))

                var advSettingsIsPressed by remember {
                    mutableStateOf(false)
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickWithRipple(
                            color = Color.White
                        ) {
                            advSettingsIsPressed = !advSettingsIsPressed
                        },

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Advanced settings",
                        modifier = modifier.padding(start = 8.dp),
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        fontSize = 15.sp
                    )

                    Icon(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .rotate(if (advSettingsIsPressed) 95f else 0f),
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Color.White.copy(0.7f)
                    )
                }


                if (advSettingsIsPressed) {
                    Spacer(modifier = modifier.height(4.dp))

                    Text(
                        text = "Reminder",
                        fontFamily = PoppinsFontFamily,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.50f),
                    )

                    Spacer(modifier = modifier.height(8.dp))


                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(color = thirtyContainerDark)
                            .clickWithRipple(
                                color = Color.White
                            ) { /*TODO*/ },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = modifier.padding(start = 16.dp),
                            text = stringResource(R.string.a_reminder_for_this_habit),
                            fontFamily = PoppinsFontFamily,
                            fontSize = 13.sp,
                            color = Color.White,
                        )

                        var isChecked by remember {
                            mutableStateOf(false)
                        }

                        Switch(
                            modifier = modifier
                                .padding(end = 12.dp)
                                .scale(0.8f),
                            checked = isChecked,
                            onCheckedChange = { isChecked = !isChecked }
                        )
                    }

                }


            }


            Button(
                modifier = modifier
                    .padding(bottom = 12.dp)
                    .fillMaxWidth(0.9f)
                    .height(60.dp)
                    .border(
                        1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(corner = CornerSize(50.dp))
                    )
                    .align(Alignment.BottomCenter),

                onClick = {
                    /*TODO*/
                    //navController.navigate(create_own_habit_navigation)
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
        }
    }
}


@Composable
private fun ExecutionTimeItem(
    modifier: Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector? = null,
) {

    Row(
        modifier = modifier
            .fillMaxWidth(0.48f)
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                color = if (!isSelected) thirtyContainerDark
                else MaterialTheme.colorScheme.primaryContainer
            )
            .clickWithRipple(color = Color.White) {
                onClick.invoke()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null)
        }

        Text(
            modifier = modifier.padding(start = 6.dp),
            text = text,
            fontFamily = PoppinsFontFamily,
            fontSize = 13.sp,
            color = Color.White,
        )

    }
}
