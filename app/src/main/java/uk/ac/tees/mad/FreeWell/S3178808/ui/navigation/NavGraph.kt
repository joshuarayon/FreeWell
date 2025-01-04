package uk.ac.tees.mad.FreeWell.S3178808.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignIn : Screen("sign_in")
    object SignUp : Screen("sign_up") // Add this line for the SignUp screen
    object Home : Screen("home")
    object Post : Screen("post")

    object Message : Screen("message/{userName}") {
        fun createRoute(userName: String) = "message/$userName"
        val arguments: List<NamedNavArgument>
            get() = listOf(navArgument("userName") { type = NavType.StringType })
    }

    object Profile : Screen("profile")

    object ProductDetails : Screen("product_details/{productName}/{uploadedBy}") {
        fun createRoute(productName: String, uploadedBy: String) =
            "product_details/$productName/$uploadedBy"
        val arguments: List<NamedNavArgument>
            get() = listOf(
                navArgument("productName") { type = NavType.StringType },
                navArgument("uploadedBy") { type = NavType.StringType }
            )
    }
    object MessageList : Screen("message_list")
    object ResetPassword : Screen("reset_password")
    object DeleteAccount : Screen("delete_account")
    object FAQs : Screen("faqs")
    object ContactUs : Screen("contact_us")
    object PrivacyPolicy : Screen("privacy_policy")
    object TermsOfService : Screen("terms_of_service")
    object Settings : Screen("settings")
}
