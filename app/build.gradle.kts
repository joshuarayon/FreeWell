plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
    id("org.jetbrains.kotlin.plugin.compose")

}

android {
    namespace = "uk.ac.tees.mad.FreeWell.S3178808"
    compileSdk = 34

    defaultConfig {
        applicationId = "uk.ac.tees.mad.FreeWell.S3178808"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Ensure Java and Kotlin targets are consistent (JVM 1.8)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // Enable Jetpack Compose
    buildFeatures {
        compose = true
    }

    // Packaging options if needed
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Align Kotlin JVM target with Java
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Firebase BoM and Auth
    implementation(platform("com.google.firebase:firebase-bom:32.1.1"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.compose.material)

    // Jetpack Compose dependencies
    val composeUiVersion = "1.5.0"
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.material:material:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.7.2")

    // Core KTX
    implementation("androidx.core:core-ktx:1.12.0")

    // Lifecycle & ViewModel for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")
// Jetpack Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.0") // Use the latest version available

    // Optional: For ViewModel support with navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")// If using Hilt

    implementation("com.google.android.gms:play-services-location:21.0.1")

}
