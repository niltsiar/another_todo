plugins {
    id("anothertodo.kotlin.library")
    id("anothertodo.kotlin.serialization")
    id("anothertodo.arrow")
    id("anothertodo.kotlinx.datetime")
}

dependencies {
    // Hilt
    implementation(libs.javax.inject)
}
