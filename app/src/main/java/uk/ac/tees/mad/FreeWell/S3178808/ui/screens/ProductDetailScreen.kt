package uk.ac.tees.mad.FreeWell.S3178808.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import uk.ac.tees.mad.FreeWell.S3178808.R

@Composable
fun ProductDetailsScreen(
    productName: String,
    imageUri: String?,          // Nullable image URI for uploaded images
    imageResourceId: Int?,      // Resource ID for predefined images
    uploadedBy: String,         // Uploader's name
    onMessageClicked: (String) -> Unit // Callback for navigation
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("$productName Details") })
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
                // Product Image Section
                when {
                    !imageUri.isNullOrEmpty() -> {
                        // Display uploaded image
                        Image(
                            painter = rememberImagePainter(data = imageUri),
                            contentDescription = productName,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    imageResourceId != null -> {
                        // Display predefined image
                        Image(
                            painter = painterResource(id = imageResourceId),
                            contentDescription = productName,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    else -> {
                        // Display placeholder when no image is available
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Image Available", color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Product Details Section
                Text(text = "Product Name: $productName", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Location: CW1 5NP", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Pickup Time: 6 PM", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Uploaded By: $uploadedBy", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))

                // Message Button
                Button(
                    onClick = { onMessageClicked(uploadedBy) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Message")
                }
            }
        }
    }
}
