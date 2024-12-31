package uk.ac.tees.mad.FreeWell.S3178808

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    private val _productList = mutableStateListOf<Product>()
    val productList: List<Product> get() = _productList

    private val predefinedProducts = listOf(
        Product(
            name = "Chicken",
            location = "Predefined",
            imageResourceId = R.drawable.chicken,
            uploadedBy = "Mike"
        ),
        Product(
            name = "Vegetables",
            location = "Predefined",
            imageResourceId = R.drawable.vegetables,
            uploadedBy = "Amy"
        ),
        Product(
            name = "Cycle",
            location = "Predefined",
            imageResourceId = R.drawable.cycle,
            uploadedBy = "Giri"
        )
    )

    init {
        if (_productList.isEmpty()) { // Prevent adding multiple times
            _productList.addAll(predefinedProducts)
        }
    }


    fun addProduct(product: Product) {
        if (!_productList.any { it.name == product.name && it.uploadedBy == product.uploadedBy }) {
            _productList.add(product)
            println("Product added: $product")
        } else {
            println("Duplicate product skipped: $product")
        }
    }
}
