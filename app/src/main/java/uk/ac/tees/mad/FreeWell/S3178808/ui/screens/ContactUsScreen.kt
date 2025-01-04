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
fun ContactUsScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Us") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Section Title
            Text(text = "Reach out to us", fontSize = 20.sp, color = Color(0xFF6200EE))
            Spacer(modifier = Modifier.height(8.dp))

            // Email
            Text(text = "Email:", fontSize = 16.sp, color = Color.Black)
            Text(text = "freeooapp@example.com", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))

            // Phone
            Text(text = "Phone:", fontSize = 16.sp, color = Color.Black)
            Text(text = "+1 234 567 890", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))

            // Office Address
            Text(text = "Office Address:", fontSize = 16.sp, color = Color.Black)
            Text(
                text = "FreeWell App\n123 Innovation Lane\nTech City, TX 75001, USA",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Social Media
            Text(text = "Follow us on social media:", fontSize = 16.sp, color = Color.Black)
            Text(text = "Facebook: facebook.com/FreeWellApp", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Twitter: twitter.com/FreeWellApp", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Instagram: instagram.com/FreeWellApp", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))

            // Support Hours
            Text(text = "Support Hours:", fontSize = 16.sp, color = Color.Black)
            Text(
                text = "Monday - Friday: 9:00 AM - 6:00 PM (CST)\n" +
                        "Saturday: 10:00 AM - 4:00 PM (CST)",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Additional Help
            Text(
                text = "If you have any questions or concerns, feel free to reach out through the channels above. " +
                        "We’re here to help!",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}
