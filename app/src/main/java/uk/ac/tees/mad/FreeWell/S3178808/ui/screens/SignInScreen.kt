// File: ui/screens/SignInScreen.kt
package uk.ac.tees.mad.FreeWell.S3178808.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import uk.ac.tees.mad.FreeWell.S3178808.R
import uk.ac.tees.mad.FreeWell.S3178808.SignInState

@Composable
fun SignInScreen(
    signInState: SignInState,
    onSignInSuccess: () -> Unit,
    onSignInError: (String) -> Unit,
    googleSignInClient: GoogleSignInClient,
    firebaseAuthWithGoogle: (String?, (Boolean, String?) -> Unit) -> Unit
) {
    val authState = remember { mutableStateOf(signInState) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data: Intent? = result.data
        if (result.resultCode == android.app.Activity.RESULT_OK && data != null) {
            val task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(com.google.android.gms.common.api.ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken) { success, errorMsg ->
                        if (success) {
                            onSignInSuccess()
                        } else {
                            onSignInError(errorMsg ?: "Unknown Error")
                        }
                    }
                }
            } catch (e: Exception) {
                onSignInError("Google Sign-In failed: ${e.message}")
            }
        } else {
            onSignInError("Sign-In canceled or failed.")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sign In") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                when (signInState) {
                    is SignInState.Idle -> {
                        SignInContent(
                            onClick = {
                                authState.value = SignInState.Loading
                                launcher.launch(googleSignInClient.signInIntent)
                            }
                        )
                    }
                    is SignInState.Loading -> {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    is SignInState.Error -> {
                        Text(
                            text = "Error: ${signInState.message}",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp)
                        )
                        SignInContent(
                            onClick = {
                                authState.value = SignInState.Loading
                                launcher.launch(googleSignInClient.signInIntent)
                            }
                        )
                    }
                    is SignInState.Success -> {
                        // Navigation handled externally
                    }
                }
            }
        }
    }
}

@Composable
fun SignInContent(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Perfect Circle App Icon
        Image(
            painter = painterResource(id = R.drawable.appicon), // Replace with your app icon resource
            contentDescription = "App Icon",
            modifier = Modifier
                .size(120.dp) // Circle size
                .clip(RoundedCornerShape(60.dp)) // Ensures perfect circular clipping
                .aspectRatio(1f, matchHeightConstraintsFirst = true), // Maintains 1:1 aspect ratio
            contentScale = ContentScale.Crop // Ensures the image fills the circle
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Welcome Message
        Text(
            text = "Welcome to Freeoo!",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Google Sign-In Button
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(24.dp),
            elevation = ButtonDefaults.elevation(8.dp),
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(0.8f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google), // Replace with your Google logo
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign in with Google",
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}
