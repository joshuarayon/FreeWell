package uk.ac.tees.mad.FreeWell.S3178808.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import uk.ac.tees.mad.FreeWell.S3178808.R

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userName = currentUser?.displayName ?: "User"
    val userEmail = currentUser?.email ?: "Email not available"
    val profileImageUrl = currentUser?.photoUrl

    val totalProductsReceived = 50
    val totalProductsGiven = 35

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") }
            )
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                // Profile Picture with placeholder
                Image(
                    painter = rememberImagePainter(
                        data = profileImageUrl,
                        builder = {
                            // If profileImageUrl is null, Coil will use 'placeholder' or 'error'
                            placeholder(R.drawable.placehold)
                            error(R.drawable.placehold)
                        }
                    ),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Greeting Text
                Text(
                    text = "Hi, $userName",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )

                // Email Text
                Text(
                    text = userEmail,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Product Stats
                Text(
                    text = "Total Products Received: $totalProductsReceived",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Total Products Given: $totalProductsGiven",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Sign Out Button
                Button(
                    onClick = onSignOut,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sign Out")
                }
            }
        }
    }
}
