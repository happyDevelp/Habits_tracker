package com.example.habitstracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.habitstracker.Calendar.CalendarItem
import com.example.habitstracker.Calendar.CalendarRowList
import com.example.habitstracker.ui.theme.AppTheme
import com.example.habitstracker.ui.theme.AppTypography
import com.example.habitstracker.utils.generateDateSequence
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppScreen()
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun AppScreen() {
    AppTheme(darkTheme = true) {
        MyScaffold()
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold() {
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

                            Text(
                                text = "31 Juli",
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
                        onClick = { /*TODO*/ },
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

            ConstraintLayout {

                val (
                    calendar,
                    habitsList,
                    createHabitsButton,
                ) = createRefs()


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .constrainAs(calendar) {  }
                ) {
                    val dateSet = generateDateSequence(LocalDate.now(), 500)

                    CalendarRowList(dateSet.toMutableList())

                }




            }

        }
    }
}



