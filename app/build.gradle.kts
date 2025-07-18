plugins {
    id("anothertodo.android.application")
    id("anothertodo.android.compose")
    id("anothertodo.android.hilt")
    id("anothertodo.kotlin.serialization")
}

android {
    namespace = "dev.niltsiar.anothertodo"
    compileSdk = 36

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

    // Navigation
    implementation(libs.androidx.navigation)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Arrow
    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    // Kotlin
    implementation(libs.kotlinx.datetime)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.property)
}
