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
            id = "anothertodo.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "anothertodo.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "anothertodo.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "anothertodo.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
        register("kotlinSerialization") {
            id = "anothertodo.kotlin.serialization"
            implementationClass = "KotlinSerializationConventionPlugin"
        }
        register("androidHilt") {
            id = "anothertodo.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "anothertodo.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmTest") {
            id = "anothertodo.jvm.test"
            implementationClass = "JvmTestConventionPlugin"
        }
    }
}
