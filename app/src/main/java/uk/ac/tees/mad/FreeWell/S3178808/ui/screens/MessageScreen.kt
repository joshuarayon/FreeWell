package uk.ac.tees.mad.FreeWell.S3178808.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign

// Data class for chat messages
data class ChatMessage(val sender: String, val message: String)

@Composable
fun MessageScreen(
    userName: String,         // Specific user to message
    onSignOut: () -> Unit     // Sign out callback
) {
    // State to hold list of messages and the input message
    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }
    var newMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat with $userName") },
                actions = {
                    // Sign Out Button
                    IconButton(onClick = onSignOut) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Sign Out")
                    }
                }
            )
        },
        bottomBar = {
            // Input Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newMessage,
                    onValueChange = { newMessage = it },
                    placeholder = { Text("Type your message...") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                Button(
                    onClick = {
                        if (newMessage.isNotBlank()) {
                            // Add new message to list
                            messages = messages + ChatMessage("You", newMessage)
                            newMessage = ""
                        }
                    },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("Send")
                }
            }
        }
    ) { paddingValues ->
        // Chat List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message, isCurrentUser = message.sender == "You")
            }
        }

        // Placeholder when there are no messages
        if (messages.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    text = "Start a conversation with $userName!",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage, isCurrentUser: Boolean) {
    Box(
        contentAlignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = if (isCurrentUser) Color(0xFFDCF8C6) else Color(0xFFE5E5EA),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = if (isCurrentUser) "You" else message.sender,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.message,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}
