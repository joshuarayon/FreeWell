# FreeWell Application

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [App Structure](#app-structure)
- [Navigation Flow](#navigation-flow)
- [Screens](#screens)
- [Future Enhancements](#future-enhancements)
- [Challenges](#challenges)

## Introduction
FreeWell is an Android application that provides a platform for users to share and discover products within a community. It simplifies the process of product posting, browsing, and interaction while integrating location services and secure authentication.

## Features
- **Authentication**:
  - Email/Password login.
  - Google Sign-In integration.
- **Product Management**:
  - Upload products with details (name, location, images).
  - View and search for products.
- **Location Services**:
  - Requires GPS or network-based location to provide localized features.
- **Messaging**:
  - Allows users to message product owners directly.
- **Profile and Settings**:
  - Manage user profile and account settings.
  - Features like password reset, FAQs, and terms of service.

## Technologies Used
- **Firebase**:
  - Firebase Authentication for secure login.
- **Google Services**:
  - Google Sign-In API.
  - Google Location Services for location-based functionality.
- **Jetpack Compose**:
  - UI framework for declarative and responsive user interfaces.
- **Coil**:
  - Image loading library for Compose.
- **Kotlin**:
  - Used for application development.

## Setup and Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/freewell-app.git
   ```
2. Open the project in Android Studio.
3. Configure Firebase:
   - Add your Firebase project's `google-services.json` file to the `app/` directory.
   - Enable Firebase Authentication for Email/Password and Google Sign-In.
4. Sync the project with Gradle.
5. Run the application on an emulator or a physical device with SDK version 26 or higher.

## App Structure
- **`MainActivity.kt`**: The entry point of the application that initializes Firebase and manages navigation.
- **`ui/screens`**:
  - Contains all composable screens (e.g., SignInScreen, HomeScreen, etc.).
- **`ui/navigation/NavGraph.kt`**:
  - Defines the navigation graph for managing screen transitions.
- **`build.gradle.kts`**:
  - Configures project dependencies and build settings.

## Navigation Flow
1. **Splash Screen**:
   - Checks user authentication.
   - Navigates to `Home` if the user is logged in; otherwise, redirects to `SignIn`.
2. **Sign-In and Sign-Up**:
   - Allows users to log in or create an account.
3. **Home Screen**:
   - Displays a grid of products.
   - Provides access to messaging, settings, and detailed product views.
4. **Other Screens**:
   - Product details, profile, messaging, settings, and more.

## Screens
1. **SignInScreen**:
   - Login functionality with email/password and Google Sign-In.
2. **SignUpScreen**:
   - User registration with profile details.
3. **HomeScreen**:
   - Product browsing and interaction.
4. **ProductDetailsScreen**:
   - Displays detailed information about a product.
5. **SettingsScreen**:
   - Manage account settings, including reset password, delete account, FAQs, etc.

## Future Enhancements
- Add support for push notifications for messaging.
- Implement a backend database for product listings.
- Improve product filtering with advanced search options.
- Add multi-language support for a wider audience.

## Challenges
- **Integrating Firebase Authentication**:
  - Ensuring secure login and logout processes.
- **Jetpack Compose Navigation**:
  - Managing dynamic navigation arguments and screen transitions.
- **Handling Location Permissions**:
  - Implementing fallback mechanisms for denied permissions.

