plugins {
    id("anothertodo.kotlin.library")
    id("anothertodo.kotlin.serialization")
}

dependencies {
    // Kotlin
    implementation(libs.kotlinx.datetime)

    // Arrow
    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    // Hilt
    implementation(libs.javax.inject)

    // Testing
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.property)
    testImplementation(libs.kotlinx.coroutines.test)
}
