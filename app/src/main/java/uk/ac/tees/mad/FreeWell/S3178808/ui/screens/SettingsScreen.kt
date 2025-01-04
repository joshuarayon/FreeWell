import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onProfileClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    onFAQsClick: () -> Unit,
    onContactUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color(0xFF6200EE),
                contentColor = Color.White
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Settings",
                fontSize = 24.sp,
                color = Color(0xFF6200EE),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            SettingsCard(
                label = "View Account Profile",
                icon = Icons.Default.AccountCircle,
                onClick = onProfileClick // Navigate to Profile Page
            )
            SettingsCard(
                label = "Reset Password",
                icon = Icons.Default.Lock,
                onClick = onResetPasswordClick // Navigate to Reset Password Page
            )
            SettingsCard(
                label = "Delete Account",
                icon = Icons.Default.Delete,
                onClick = onDeleteAccountClick // Navigate to Delete Account Page
            )
            Divider(color = Color.Gray, thickness = 1.dp)
            SettingsCard(
                label = "FAQs",
                icon = Icons.Default.Info,
                onClick = onFAQsClick // Navigate to FAQs Page
            )
            SettingsCard(
                label = "Contact Us",
                icon = Icons.Default.Email,
                onClick = onContactUsClick // Navigate to Contact Us Page
            )
            Divider(color = Color.Gray, thickness = 1.dp)
            SettingsCard(
                label = "Privacy Policy",
                icon = Icons.Default.Lock,
                onClick = onPrivacyPolicyClick // Navigate to Privacy Policy Page
            )
            SettingsCard(
                label = "Terms of Service",
                icon = Icons.Default.AccountBox,
                onClick = onTermsOfServiceClick // Navigate to Terms of Service Page
            )
        }
    }
}

@Composable
fun SettingsCard(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFF5F5F5),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF6200EE), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, fontSize = 16.sp, color = Color.Black)
        }
    }
}
