plugins {
    id("dev.niltsiar.anothertodo.android.library")
    id("dev.niltsiar.anothertodo.android.compose")
    id("dev.niltsiar.anothertodo.android.hilt")
    id("dev.niltsiar.anothertodo.kotlin.serialization")
    id("dev.niltsiar.anothertodo.arrow")
    id("dev.niltsiar.anothertodo.navigation")
    id("dev.niltsiar.anothertodo.kotlinx.datetime")
}

android {
    namespace = "dev.niltsiar.anothertodo.presentation"
}

dependencies {
    // Domain module
    implementation(project(":domain"))
}
