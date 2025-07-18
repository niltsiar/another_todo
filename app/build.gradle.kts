plugins {
    id("anothertodo.android.application")
    id("anothertodo.android.compose")
    id("anothertodo.android.hilt")
    id("anothertodo.kotlin.serialization")
    id("anothertodo.arrow")
    id("anothertodo.navigation")
    id("anothertodo.kotlinx.datetime")
    id("anothertodo.android.room")
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

    // Testing
    testImplementation(libs.junit)
}
