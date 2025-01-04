package uk.ac.tees.mad.FreeWell.S3178808.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TermsOfServiceScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terms of Service") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color(0xFF6200EE),
                contentColor = Color.White
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Terms of Service", fontSize = 20.sp, color = Color(0xFF6200EE))
            Divider(color = Color.Gray)

            // Term 1
            Text("1. Use of Services", fontSize = 16.sp, color = Color.Black)
            Text(
                "You agree to use our services only for lawful purposes and in compliance with " +
                        "all applicable laws and regulations."
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Term 2
            Text("2. Account Responsibilities", fontSize = 16.sp, color = Color.Black)
            Text(
                "You are responsible for maintaining the confidentiality of your account information. " +
                        "Any activity conducted under your account is your responsibility."
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Term 3
            Text("3. Service Availability", fontSize = 16.sp, color = Color.Black)
            Text(
                "We strive to keep our services available at all times but do not guarantee uninterrupted " +
                        "access. Downtime may occur due to maintenance or unforeseen issues."
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Term 4
            Text("4. Prohibited Activities", fontSize = 16.sp, color = Color.Black)
            Text(
                "You must not engage in activities such as hacking, spamming, or distributing malicious content " +
                        "that could harm the app, its users, or third parties."
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Term 5
            Text("5. Limitation of Liability", fontSize = 16.sp, color = Color.Black)
            Text(
                "We are not liable for any indirect, incidental, or consequential damages arising from your " +
                        "use of the app or services."
            )

        }
    }
}

