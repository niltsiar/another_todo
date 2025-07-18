plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.compose)
    alias(libs.plugins.convention.android.hilt)
    alias(libs.plugins.convention.kotlin.serialization)
    alias(libs.plugins.convention.arrow)
    alias(libs.plugins.convention.navigation)
    alias(libs.plugins.convention.kotlinx.datetime)
    alias(libs.plugins.convention.android.room)
}

android {
    namespace = "dev.niltsiar.anothertodo"

    defaultConfig {
        applicationId = "dev.niltsiar.anothertodo"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    // Modules
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))
}
