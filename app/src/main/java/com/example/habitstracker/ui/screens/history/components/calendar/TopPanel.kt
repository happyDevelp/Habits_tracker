package com.example.habitstracker.ui.screens.history.components.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.ui.custom.MyText
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopPanel(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    minusMonth: () -> Unit,
    plusMonth: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val formatter = DateTimeFormatter.ofPattern(
            stringResource(id = R.string.month_and_year_pattern),
            Locale.getDefault()
        )
        val formattedCurrentDate = currentDate.format(formatter)

        IconButton(onClick = { minusMonth.invoke() }) {
            Icon(
                modifier = modifier,
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = stringResource(R.string.previous_month),
                tint = Color.White.copy(0.85f)
            )
        }

        MyText(modifier = modifier, text = formattedCurrentDate, textSize = 15.sp)

        val now = LocalDate.now()
        val isEnable = currentDate.year < now.year ||
            currentDate.monthValue < now.monthValue
        IconButton(
            onClick = { plusMonth.invoke() },
            enabled = isEnable
        ) {
            Icon(
                modifier = modifier,
                imageVector = Icons.Default.ChevronRight,
                contentDescription = stringResource(R.string.next_month),
                tint = if (isEnable) Color.White.copy(0.85f) else Color.Transparent
            )
        }
    }
}