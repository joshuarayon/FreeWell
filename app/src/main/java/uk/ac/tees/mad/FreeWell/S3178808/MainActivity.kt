package uk.ac.tees.mad.FreeWell.S3178808

import HomeScreen
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import uk.ac.tees.mad.FreeWell.S3178808.ui.navigation.Screen
import uk.ac.tees.mad.FreeWell.S3178808.ui.screens.*
import uk.ac.tees.mad.FreeWell.S3178808.ui.theme.FreeWellTheme

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            FreeWellTheme {
                val navController = rememberNavController()
                val productViewModel: ProductViewModel = viewModel()

                LocationAwareContent(
                    navController = navController,
                    productViewModel = productViewModel,
                    auth = auth,
                    googleSignInClient = googleSignInClient
                )
            }
        }
    }
}

@Composable
fun LocationAwareContent(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    auth: FirebaseAuth,
    googleSignInClient: GoogleSignInClient
) {
    var locationEnabled by remember { mutableStateOf(false) }
    val context = LocalContext.current // Access LocalContext in a @Composable context

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val locationManager = context.getSystemService(LocationManager::class.java)
            val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            locationEnabled = isLocationEnabled
        } else {
            locationEnabled = false
        }
    }

    // Trigger permission request using LaunchedEffect
    LaunchedEffect(Unit) {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    if (locationEnabled) {
        AppNavigation(
            navController = navController,
            productViewModel = productViewModel,
            auth = auth,
            googleSignInClient = googleSignInClient
        )
    } else {
        EnableLocationPrompt()
    }
}

@Composable
private fun EnableLocationPrompt() {
    // UI prompt for user to enable location services
    Text("Please enable location services to proceed.")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    auth: FirebaseAuth,
    googleSignInClient: GoogleSignInClient
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashComplete = {
                    if (auth.currentUser != null) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.SignIn.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        // Sign-In Screen
        composable(Screen.SignIn.route) {
            var signInState by remember { mutableStateOf<SignInState>(SignInState.Idle) }

            SignInScreen(
                googleSignInClient = googleSignInClient,
                firebaseAuthWithGoogle = { idToken, callback ->
                    firebaseAuthWithGoogle(auth, idToken, callback)
                },
                signInState = signInState,
                onSignInError = { errorMsg ->
                    signInState = SignInState.Error(errorMsg)
                },
                onSignInSuccess = {
                    signInState = SignInState.Success
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
            )
        }

        // Home Screen
        composable(Screen.Home.route) {
            HomeScreen(
                productViewModel = productViewModel,
                onSignOut = { signOut(auth, googleSignInClient, navController) },
                onPostClicked = { navController.navigate(Screen.Post.route) },
                onMessageListClicked = { navController.navigate(Screen.MessageList.route) },
                onProfileClicked = { navController.navigate(Screen.Profile.route) },
                onProductClicked = { product ->
                    navController.navigate(Screen.ProductDetails.createRoute(product.name, product.uploadedBy))
                }
            )
        }

        // Product Details Screen
        composable(
            route = Screen.ProductDetails.route,
            arguments = Screen.ProductDetails.arguments
        ) { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("productName") ?: "Unknown Product"
            val uploadedBy = backStackEntry.arguments?.getString("uploadedBy") ?: "Unknown User"

            val product = productViewModel.productList.find { it.name == productName && it.uploadedBy == uploadedBy }

            ProductDetailsScreen(
                productName = productName,
                imageUri = product?.imageUri,
                imageResourceId = product?.imageResourceId,
                uploadedBy = uploadedBy,
                onMessageClicked = { userName ->
                    navController.navigate(Screen.Message.createRoute(userName))
                }
            )
        }

        // Post Screen
        composable(Screen.Post.route) {
            PostScreen(
                onSignOut = { signOut(auth, googleSignInClient, navController) },
                onSubmit = { productName, location, imageUri ->
                    val uploadedBy = auth.currentUser?.displayName ?: "Unknown User"
                    productViewModel.addProduct(
                        Product(
                            name = productName,
                            location = location,
                            imageUri = imageUri?.toString(),
                            uploadedBy = uploadedBy
                        )
                    )
                    navController.popBackStack()
                }
            )
        }

        // Message List Screen
        composable(Screen.MessageList.route) {
            MessageListScreen(
                onUserClicked = { userName ->
                    navController.navigate(Screen.Message.createRoute(userName))
                }
            )
        }

        // Message Screen
        composable(
            route = Screen.Message.route,
            arguments = Screen.Message.arguments
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: "Unknown User"
            MessageScreen(
                userName = userName,
                onSignOut = { navController.popBackStack() }
            )
        }

        // Profile Screen
        composable(Screen.Profile.route) {
            ProfileScreen(onSignOut = { signOut(auth, googleSignInClient, navController) })
        }
    }
}

private fun firebaseAuthWithGoogle(auth: FirebaseAuth, idToken: String?, onResult: (Boolean, String?) -> Unit) {
    if (idToken == null) {
        onResult(false, "Missing ID token")
        return
    }
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true, null)
            } else {
                onResult(false, task.exception?.message)
            }
        }
}

private fun signOut(auth: FirebaseAuth, googleSignInClient: GoogleSignInClient, navController: NavHostController) {
    auth.signOut()
    googleSignInClient.signOut().addOnCompleteListener {
        navController.navigate(Screen.SignIn.route) {
            popUpTo(Screen.Home.route) { inclusive = true }
        }
    }
}

// Sign-In State
sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val message: String) : SignInState()
}
