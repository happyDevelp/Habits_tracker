package com.example.habitstracker.history.presentation.tab_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.core.presentation.theme.screenContainerBackgroundDark
import com.example.habitstracker.core.presentation.theme.screensBackgroundDark

@Preview(showSystemUi = true)
@Composable
fun AchievementsScreenPreview(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(screensBackgroundDark)
    )
    { AchievementsScreen() }
}

@Composable
fun AchievementsScreen(modifier: Modifier = Modifier) {
    val globalPadding = 16.dp
    Card(
        modifier = modifier
            .padding(globalPadding),
        colors = CardDefaults.cardColors(containerColor = screenContainerBackgroundDark)
    ) {
        Column(
            modifier = modifier
                .padding(vertical = 28.dp,
                    horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Habits Finished",
                fontSize = 20.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "4/6 achieved",
                fontSize = 11.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.White.copy(0.7f),
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                modifier = Modifier.wrapContentHeight(),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                userScrollEnabled = false
            ) {
                val habitsAchievements = listOf<String>(
                    "1",
                    "10",
                    "25",
                    "100",
                    "500",
                    "1000",
                )

                items(habitsAchievements.size) { index ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier.width(150.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.dart_board_target_svgrepo_com),
                                contentDescription = "achievements picture",
                                modifier = Modifier.size(80.dp),
                                tint = Color.Unspecified,
                            )
                            Image(
                                painter = painterResource(id = R.drawable.svgrepo_iconcarrier),
                                contentDescription = "Ribbon",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(28.dp)
                                    .offset(x = (-2).dp, y = 6.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Text(
                                text = habitsAchievements[index],
                                fontSize = 14.sp,
                                fontFamily = PoppinsFontFamily,
                                color = blueColor,
                                modifier = Modifier.offset(x = (-2).dp, y = (-1.9).dp),
                                textAlign = TextAlign.Center,

                                )
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(
                            text = if(index == 0) "Finish Your Habit First Time"
                                else "Finish Habit For The ${habitsAchievements[index]} Times",
                            fontSize = 11.sp,
                            fontFamily = PoppinsFontFamily,
                            color = Color.White.copy(0.9f),
                            modifier = Modifier.width(80.dp + 8.dp),
                            textAlign = TextAlign.Center,
                            softWrap = true,
                            overflow = TextOverflow.Clip,
                        )

                    }

                }
            }

        }
    }
}

/*
data class AchievementProperty(
    val text: String
        )*/
