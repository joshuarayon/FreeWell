import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import uk.ac.tees.mad.FreeWell.S3178808.Product
import uk.ac.tees.mad.FreeWell.S3178808.ProductViewModel
import uk.ac.tees.mad.FreeWell.S3178808.R

@Composable
fun HomeScreen(
    productViewModel: ProductViewModel, // ViewModel for uploaded products
    onSignOut: () -> Unit,
    onPostClicked: () -> Unit,
    onMessageListClicked: () -> Unit, // Navigate to MessageListScreen
    onProfileClicked: () -> Unit,
    onProductClicked: (Product) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = onProfileClicked) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onPostClicked = onPostClicked,
                onMessageListClicked = onMessageListClicked // Updated to navigate to MessageListScreen
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            Text(
                text = "Caring is Sharing!!!",
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )

            // Combined Grid for Products
            CombinedProductGrid(productViewModel, onProductClicked)
        }
    }
}

@Composable
fun CombinedProductGrid(
    productViewModel: ProductViewModel,
    onProductClicked: (Product) -> Unit
) {
    val products = productViewModel.productList

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->
            ProductItem(product = product, onClick = { onProductClicked(product) })
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        // Image Handling
        if (product.imageUri != null) {
            // Display uploaded image
            Image(
                painter = rememberImagePainter(data = product.imageUri),
                contentDescription = product.name,
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            // Display predefined image
            Image(
                painter = painterResource(
                    id = product.imageResourceId ?: R.drawable.img
                ),
                contentDescription = product.name,
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.name, fontSize = 16.sp)
    }
}

@Composable
fun BottomNavigationBar(
    onPostClicked: () -> Unit,
    onMessageListClicked: () -> Unit // New callback for navigating to the message list
) {
    BottomNavigation(elevation = 8.dp) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Post") },
            label = { Text("Post") },
            selected = false,
            onClick = onPostClicked
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Email, contentDescription = "Messages") },
            label = { Text("Messages") },
            selected = false,
            onClick = onMessageListClicked // Updated callback
        )
    }
}
