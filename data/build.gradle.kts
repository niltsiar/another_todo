plugins {
    id("anothertodo.android.library")
    id("anothertodo.android.hilt")
    id("anothertodo.android.room")
    id("anothertodo.kotlin.serialization")
}

android {
    namespace = "dev.niltsiar.anothertodo.data"
    compileSdk = 36
}

dependencies {
    // Domain module
    implementation(project(":domain"))

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
