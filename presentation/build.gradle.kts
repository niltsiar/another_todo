plugins {
    id("anothertodo.android.library")
    id("anothertodo.android.compose")
    id("anothertodo.android.hilt")
    id("anothertodo.kotlin.serialization")
    id("anothertodo.arrow")
    id("anothertodo.navigation")
    id("anothertodo.kotlinx.datetime")
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
}
