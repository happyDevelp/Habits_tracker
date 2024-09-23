package com.example.habitstracker.ui.screens.today_main.scaffold


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.habitstracker.R
import com.example.habitstracker.navigation.RoutesMainScreen
import com.example.habitstracker.ui.custom.MyText
import com.example.habitstracker.ui.theme.AppTypography
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarMainScreen(
    modifier: Modifier,
    navController: NavController,
) {
    TopAppBar(
        modifier = modifier.padding(vertical = 8.dp),

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

                    MyText(
                        text = "Today",
                        textSize = 26.sp,
                    )

                    val today = LocalDate.now()
                    val formatter = DateTimeFormatter.ofPattern("MMMM")
                    val month = today.format(formatter)
                    Text(
                        text = "${today.dayOfMonth} $month",
                        fontFamily = PoppinsFontFamily,
                        color = Color.White.copy(0.7f),
                        fontSize = 12.sp,
                    )
                }
            }
        },

        // FloatButton
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(RoutesMainScreen.AddHabit.route)
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
}