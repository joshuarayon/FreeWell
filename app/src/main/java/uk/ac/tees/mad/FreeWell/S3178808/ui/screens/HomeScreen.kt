import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.*
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
@Composable
fun HomeScreen(
    productViewModel: ProductViewModel, // ViewModel for uploaded products
    onSignOut: () -> Unit,
    onPostClicked: () -> Unit,
    onMessageListClicked: () -> Unit, // Navigate to MessageListScreen
    onProfileClicked: () -> Unit,
    onSettingsClicked: () -> Unit, // Add type annotation here
    onProductClicked: (Product) -> Unit
) {
    var filteredProducts by remember { mutableStateOf(productViewModel.productList) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = onProfileClicked) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onPostClicked = onPostClicked,
                onMessageListClicked = onMessageListClicked,
                onSettingsClicked = onSettingsClicked // Pass the parameter here
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            StyledSearchBar(
                placeholderText = "Search products...",
                onSearch = { query ->
                    filteredProducts = if (query.isBlank()) {
                        productViewModel.productList
                    } else {
                        productViewModel.productList.filter { product ->
                            product.name.contains(query, ignoreCase = true)
                        }
                    }
                }
            )
            CombinedProductGrid(filteredProducts, onProductClicked)
        }
    }
}


@Composable
fun StyledSearchBar(
    placeholderText: String,
    onSearch: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colors.surface)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(horizontal = 8.dp)
        ) {
            // Search Icon
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(start = 8.dp)
            )

            // Search TextField
            TextField(
                value = searchQuery,
                onValueChange = { query -> searchQuery = query },
                placeholder = { Text(placeholderText) },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.surface,
                    unfocusedIndicatorColor = MaterialTheme.colors.surface,
                    textColor = MaterialTheme.colors.onSurface
                )
            )

            // "GO" Button
            Button(
                onClick = { onSearch(searchQuery) },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("GO")
            }
        }
    }
}

@Composable
fun CombinedProductGrid(
    products: List<Product>,
    onProductClicked: (Product) -> Unit
) {
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
    onMessageListClicked: () -> Unit,
    onSettingsClicked: () -> Unit // New callback for Settings
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Post",
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.size(36.dp)
                )
            },
            label = {
                Text(
                    "Post",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            selected = false,
            onClick = onPostClicked
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Messages",
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.size(36.dp)
                )
            },
            label = {
                Text(
                    "Messages",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            selected = false,
            onClick = onMessageListClicked
        )

        // New Settings Button
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.size(36.dp)
                )
            },
            label = {
                Text(
                    "Settings",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            selected = false,
            onClick = onSettingsClicked
        )
    }
}
