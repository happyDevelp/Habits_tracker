package com.example.habitstracker

import android.os.Bundle
import android.widget.CalendarView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.AppTheme
import com.example.habittracker.ui.theme.AppTypography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppScreen()
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun AppScreen() {
    AppTheme(darkTheme = true) {
        MyScaffold()
    }
}


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
                            verticalArrangement = Arrangement.Center
                        ) {


                        }

                        Column(
                            modifier = Modifier.align(Alignment.CenterStart)
                                .padding(top = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "Today",
                                fontSize = AppTypography.headlineMedium.fontSize,
                                style = MaterialTheme.typography.titleSmall
                            )

                            Text(
                                text = "22 Juli",
                                fontSize = 10.sp,
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
        paddingValues

        val context = LocalContext.current

        /*CalendarView(context).apply {


        }*/


    }
}
