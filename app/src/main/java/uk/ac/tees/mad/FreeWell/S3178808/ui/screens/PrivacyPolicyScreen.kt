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
fun PrivacyPolicyScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy") },
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
            Text(text = "Privacy Policy", fontSize = 20.sp, color = Color(0xFF6200EE))
            Divider(color = Color.Gray)

            // Section 1: Data Collection
            Text("1. Data Collection", fontSize = 16.sp, color = Color.Black)
            Text(
                "We collect personal information, including your name, email address, and other details, " +
                        "only when necessary for providing our services."
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Section 2: Data Usage
            Text("2. Data Usage", fontSize = 16.sp, color = Color.Black)
            Text(
                "Your personal data is used solely for enhancing your experience with the app. " +
                        "We may use your information for account management, customer support, and app improvements."
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Section 3: Data Sharing
            Text("3. Data Sharing", fontSize = 16.sp, color = Color.Black)
            Text(
                "We do not sell or share your data with third parties, except when required by law " +
                        "or when explicitly authorized by you."
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Section 4: Security
            Text("4. Data Security", fontSize = 16.sp, color = Color.Black)
            Text(
                "We implement advanced security measures to protect your data from unauthorized access, " +
                        "alteration, or misuse. However, no method of data transmission over the internet is 100% secure."
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Section 5: Cookies and Analytics
            Text("5. Cookies and Analytics", fontSize = 16.sp, color = Color.Black)
            Text(
                "We may use cookies to enhance user experience and track app performance. " +
                        "You can disable cookies in your browser settings if you prefer."
            )


        }
    }
}
