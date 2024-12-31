pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        // Android Gradle Plugin
        id("com.android.application") version "8.1.0"
        // Kotlin plugin
        kotlin("android") version "1.9.10"
        // Compose compiler plugin
        id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
        // Google services plugin
        id("com.google.gms.google-services") version "4.3.15"

    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyFreeoApp"
include(":app")
