package uk.ac.tees.mad.FreeWell.S3178808

data class Product(
    val name: String,
    val location: String,
    val imageUri: String? = null,       // For uploaded images
    val imageResourceId: Int? = null,  // For predefined drawable resources
    val uploadedBy: String             // Name of the uploader
)
