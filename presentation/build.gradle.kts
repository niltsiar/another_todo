plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.compose)
    alias(libs.plugins.convention.android.hilt)
    alias(libs.plugins.convention.kotlin.serialization)
    alias(libs.plugins.convention.arrow)
    alias(libs.plugins.convention.navigation)
    alias(libs.plugins.convention.kotlinx.datetime)
}

android {
    namespace = "dev.niltsiar.anothertodo.presentation"
}

dependencies {
    // Domain module
    implementation(project(":domain"))
}
