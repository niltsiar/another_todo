plugins {
    id("dev.niltsiar.anothertodo.android.application")
    id("dev.niltsiar.anothertodo.android.compose")
    id("dev.niltsiar.anothertodo.android.hilt")
    id("dev.niltsiar.anothertodo.kotlin.serialization")
    id("dev.niltsiar.anothertodo.arrow")
    id("dev.niltsiar.anothertodo.navigation")
    id("dev.niltsiar.anothertodo.kotlinx.datetime")
    id("dev.niltsiar.anothertodo.android.room")
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
