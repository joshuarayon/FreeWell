package uk.ac.tees.mad.FreeWell.S3178808.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onSignUpError: (String) -> Unit,
    firebaseAuth: FirebaseAuth
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sign Up") },
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
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    StyledTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = "First Name"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StyledTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = "Last Name"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StyledTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StyledTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            isLoading = true
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        val user = firebaseAuth.currentUser
                                        user?.updateProfile(
                                            com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                                .setDisplayName("$firstName $lastName")
                                                .build()
                                        )?.addOnCompleteListener { profileTask ->
                                            if (profileTask.isSuccessful) {
                                                onSignUpSuccess()
                                            } else {
                                                onSignUpError(profileTask.exception?.message ?: "Could not update profile.")
                                            }
                                        }
                                    } else {
                                        val errorMsg = task.exception?.message ?: "Sign-Up failed."
                                        onSignUpError(errorMsg)
                                    }
                                }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        shape = RoundedCornerShape(24.dp),
                        elevation = ButtonDefaults.elevation(8.dp),
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth(0.8f)
                    ) {
                        Text(
                            text = "Sign Up",
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        }
    }
}
