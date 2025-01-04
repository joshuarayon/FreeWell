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
fun FAQsScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FAQs") },
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
            Text(text = "Frequently Asked Questions", fontSize = 18.sp, color = Color(0xFF6200EE))
            Divider()
            Text("Q1: How can I reset my password?")
            Text("A: Go to the Reset Password page and enter your email.")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Q2: How do I contact support?")
            Text("A: Use the Contact Us page to reach out to us.")
            Spacer(modifier = Modifier.height(8.dp))
            // FAQ 3
            Text("Q3: How do I delete my account?", fontSize = 16.sp, color = Color.Black)
            Text("A: Navigate to the Delete Account page and confirm your decision.", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // FAQ 4
            Text("Q4: Can I update my profile information?", fontSize = 16.sp, color = Color.Black)
            Text("A: Yes, you can update your profile on the View Account Profile page.", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // FAQ 5
            Text("Q5: Is my data secure?", fontSize = 16.sp, color = Color.Black)
            Text("A: Yes, your data is protected, and we adhere to strict privacy policies.", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // FAQ 6
            Text("Q6: How can I report a bug or issue?", fontSize = 16.sp, color = Color.Black)
            Text("A: Use the Contact Us page to report bugs or issues you encounter.", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // FAQ 7
            Text("Q7: Can I retrieve a deleted account?", fontSize = 16.sp, color = Color.Black)
            Text("A: Unfortunately, once deleted, accounts cannot be restored.", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))


        }
    }
}
