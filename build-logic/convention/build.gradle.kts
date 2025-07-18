import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "dev.niltsiar.anothertodo.buildlogic"

// Configure the build-logic plugins to target JDK 17
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "dev.niltsiar.anothertodo.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "dev.niltsiar.anothertodo.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "dev.niltsiar.anothertodo.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "dev.niltsiar.anothertodo.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
        register("kotlinSerialization") {
            id = "dev.niltsiar.anothertodo.kotlin.serialization"
            implementationClass = "KotlinSerializationConventionPlugin"
        }
        register("androidHilt") {
            id = "dev.niltsiar.anothertodo.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "dev.niltsiar.anothertodo.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmTest") {
            id = "dev.niltsiar.anothertodo.jvm.test"
            implementationClass = "JvmTestConventionPlugin"
        }
        register("arrow") {
            id = "dev.niltsiar.anothertodo.arrow"
            implementationClass = "ArrowConventionPlugin"
        }
        register("navigation") {
            id = "dev.niltsiar.anothertodo.navigation"
            implementationClass = "NavigationConventionPlugin"
        }
        register("kotlinxDateTime") {
            id = "dev.niltsiar.anothertodo.kotlinx.datetime"
            implementationClass = "KotlinxDateTimeConventionPlugin"
        }
    }
}
