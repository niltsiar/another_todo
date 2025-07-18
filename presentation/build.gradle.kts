plugins {
    id("anothertodo.android.library")
    id("anothertodo.android.compose")
    id("anothertodo.android.hilt")
    id("anothertodo.kotlin.serialization")
}

android {
    namespace = "dev.niltsiar.anothertodo.presentation"
    compileSdk = 36
}

dependencies {
    // Domain module
    implementation(project(":domain"))

    // AndroidX
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Navigation
    implementation(libs.androidx.navigation)

    // Kotlin
    implementation(libs.kotlinx.datetime)

    // Arrow
    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    // Testing
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.property)
    testImplementation(libs.kotlinx.coroutines.test)
}
