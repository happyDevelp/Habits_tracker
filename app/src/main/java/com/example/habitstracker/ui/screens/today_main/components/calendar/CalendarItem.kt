package com.example.habitstracker.ui.screens.today_main.components.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.ui.theme.MyFontFamily
import com.example.habitstracker.ui.theme.QuickSandFontFamily
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFF242C33)
@Composable
fun CalendarItem(
    date: LocalDate? = LocalDate.now(),
    isSelected: Boolean = false,
    onItemClicked: (() -> Unit)? = null,
) {

    val color = MaterialTheme.colorScheme.primaryContainer
    Box(
        modifier = Modifier
            .size(width = 50.dp, height = 65.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()

        ) {
            Text(
                text = date!!.dayOfWeek.name.substring(0, 3),
                fontSize = 13.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontFamily = QuickSandFontFamily,
            )

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 19.dp)
                    .clickable { onItemClicked?.invoke() }
            ) {
                Text(
                    modifier = Modifier.drawBehind {
                        if (isSelected)
                            drawCircle(
                                radius = this.size.maxDimension / 1.2f,
                                color = color
                            )
                        else {
                            drawCircle(
                                radius = this.size.maxDimension / 0,
                                color = color
                            )
                        }
                    },

                    color = Color.White,
                    text = date.dayOfMonth.toString(),
                    fontSize = 20.sp,
                    fontFamily = MyFontFamily,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

    }

}
