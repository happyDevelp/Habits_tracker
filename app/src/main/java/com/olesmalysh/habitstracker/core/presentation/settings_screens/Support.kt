package com.olesmalysh.habitstracker.core.presentation.settings_screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.olesmalysh.habitstracker.app.LocalNavController
import com.olesmalysh.habitstracker.core.presentation.theme.AppTheme
import com.olesmalysh.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.olesmalysh.habitstracker.core.presentation.theme.screenBackgroundDark

private const val SUPPORT_EMAIL = "bestie.interactive@gmail.com"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    var subject by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }
    val canSend = message.trim().isNotEmpty()

    Scaffold(
        modifier = modifier.padding(vertical = 8.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Support",
                            color = Color.White,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 20.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = "Go Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = screenBackgroundDark
                ),
                actions = {
                    IconButton(onClick = { /* no-op */ }) { }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(screenBackgroundDark)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Write to the developer",
                color = Color.White,
                fontFamily = PoppinsFontFamily,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "We’ll open your email app with your message.",
                color = Color(0xFFB8B8B8),
                fontFamily = PoppinsFontFamily,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color.White,
                    fontFamily = PoppinsFontFamily,
                    fontSize = 14.sp
                ),
                label = { Text("Subject", fontFamily = PoppinsFontFamily) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF5865F2),
                    unfocusedBorderColor = Color(0xFF2A2A2A),
                    focusedLabelColor = Color(0xFF5865F2),
                    unfocusedLabelColor = Color(0xFF8C8C8C),
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                textStyle = TextStyle(
                    color = Color.White,
                    fontFamily = PoppinsFontFamily,
                    fontSize = 14.sp
                ),
                label = { Text("Message", fontFamily = PoppinsFontFamily) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF5865F2),
                    unfocusedBorderColor = Color(0xFF2A2A2A),
                    focusedLabelColor = Color(0xFF5865F2),
                    unfocusedLabelColor = Color(0xFF8C8C8C),
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    openSupportEmail(
                        context = context,
                        to = SUPPORT_EMAIL,
                        subject = subject.ifBlank { "Habit Tracker Support" },
                        body = message.trim()
                    )
                },
                enabled = canSend,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5865F2),
                    disabledContainerColor = Color(0xFF2A2A2A),
                    contentColor = Color.White,
                    disabledContentColor = Color(0xFF8C8C8C)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Send",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Email: $SUPPORT_EMAIL",
                color = Color(0xFF8C8C8C),
                fontFamily = PoppinsFontFamily,
                fontSize = 12.sp
            )
        }
    }
}

private fun openSupportEmail(
    context: Context,
    to: String,
    subject: String,
    body: String
) {
    // We use ACTION_SENDTO with "mailto:" to ensure only email apps are launched.
    // Instead of building a complex query string, we pass data via Intent Extras.
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")

        putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    try {
        context.startActivity(Intent.createChooser(intent, "Send email"))
    } catch (_: ActivityNotFoundException) {
        // No email apps installed handling
    }
}

@Preview
@Composable
private fun PreviewSupportScreen() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            SupportScreen()
        }
    }
}
