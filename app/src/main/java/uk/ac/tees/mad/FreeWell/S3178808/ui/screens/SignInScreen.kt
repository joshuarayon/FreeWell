// File: ui/screens/SignInScreen.kt
package uk.ac.tees.mad.FreeWell.S3178808.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
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

    // Launcher to handle the result of the Google Sign-In Intent
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
                title = { Text("Sign In") }
            )
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (signInState) {
                is SignInState.Idle -> {
                    SignInButton(onClick = {
                        authState.value = SignInState.Loading
                        launcher.launch(googleSignInClient.signInIntent)
                    })
                }
                is SignInState.Loading -> {
                    CircularProgressIndicator()
                }
                is SignInState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: ${signInState.message}")
                        Spacer(modifier = Modifier.height(8.dp))
                        SignInButton(onClick = {
                            authState.value = SignInState.Loading
                            launcher.launch(googleSignInClient.signInIntent)
                        })
                    }
                }
                is SignInState.Success -> {
                    // No UI change needed; navigation handled externally
                }
            }
        }
    }
}

@Composable
fun SignInButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Sign in with Google")
    }
}
