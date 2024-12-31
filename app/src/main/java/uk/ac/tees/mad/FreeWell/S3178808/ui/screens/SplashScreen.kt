package uk.ac.tees.mad.FreeWell.S3178808.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.delay
import uk.ac.tees.mad.FreeWell.S3178808.R



@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit // Callback to navigate after splash duration
) {
    LaunchedEffect(Unit) {
        delay(2000) // Delay for 2 seconds
        onSplashComplete()
    }

    // Full-screen image for the splash background
    Box(
        contentAlignment = Alignment.Center, // Center the content in the Box
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Replace with your image resource
            contentDescription = "Welcome to FreeoO",
            modifier = Modifier.fillMaxSize(), // Fill the entire screen
            contentScale = ContentScale.Crop // Crop the image to fit the screen without distortion
        )
    }
}
