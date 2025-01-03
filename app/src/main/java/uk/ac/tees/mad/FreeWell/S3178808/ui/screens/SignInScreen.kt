// File: ui/screens/SignInScreen.kt
package uk.ac.tees.mad.FreeWell.S3178808.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
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
    firebaseAuthWithGoogle: (String?, (Boolean, String?) -> Unit) -> Unit,
    firebaseAuth: FirebaseAuth,
    onSignUpClick: () -> Unit // Add this parameter
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
                when (val state = authState.value) {
                    is SignInState.Idle -> {
                        SignInContent(
                            email = email,
                            onEmailChange = { email = it },
                            password = password,
                            onPasswordChange = { password = it },
                            onSignInClick = {
                                authState.value = SignInState.Loading
                                firebaseAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            onSignInSuccess()
                                        } else {
                                            onSignInError(task.exception?.message ?: "Sign-In failed.")
                                            authState.value = SignInState.Idle
                                        }
                                    }
                            },
                            onGoogleSignInClick = {
                                authState.value = SignInState.Loading
                                launcher.launch(googleSignInClient.signInIntent)
                            },
                            onSignUpClick = onSignUpClick // Pass the callback here
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
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp)
                        )
                        SignInContent(
                            email = email,
                            onEmailChange = { email = it },
                            password = password,
                            onPasswordChange = { password = it },
                            onSignInClick = {
                                authState.value = SignInState.Loading
                                firebaseAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            onSignInSuccess()
                                        } else {
                                            onSignInError(task.exception?.message ?: "Sign-In failed.")
                                            authState.value = SignInState.Idle
                                        }
                                    }
                            },
                            onGoogleSignInClick = {
                                authState.value = SignInState.Loading
                                launcher.launch(googleSignInClient.signInIntent)
                            },
                            onSignUpClick = onSignUpClick // Pass the callback here
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
fun SignInContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.appicon),
            contentDescription = "App Icon",
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(60.dp))
                .aspectRatio(1f, matchHeightConstraintsFirst = true),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome to Freeoo!",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        StyledTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email"
        )

        Spacer(modifier = Modifier.height(8.dp))

        StyledTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSignInClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(24.dp),
            elevation = ButtonDefaults.elevation(8.dp),
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text(
                text = "Sign In",
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.button
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onGoogleSignInClick,
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
                    painter = painterResource(id = R.drawable.google),
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

        Spacer(modifier = Modifier.height(16.dp))

        // Adding "OR" line
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            )
            Text(
                text = "OR",
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            )
        }

        // Adding "Don't have an account? Register" as a clickable text
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface)
            )
            Text(
                text = "Register",
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.primary,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.clickable(onClick = onSignUpClick)
            )
        }
    }
}


@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}