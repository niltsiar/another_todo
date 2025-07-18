plugins {
    id("anothertodo.android.library")
    id("anothertodo.android.hilt")
    id("anothertodo.android.room")
    id("anothertodo.kotlin.serialization")
    id("anothertodo.arrow")
    id("anothertodo.kotlinx.datetime")
}

android {
    namespace = "dev.niltsiar.anothertodo.data"
    compileSdk = 36
}

dependencies {
    // Domain module
    implementation(project(":domain"))
}
