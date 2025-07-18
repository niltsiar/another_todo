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
            id = libs.plugins.convention.android.application.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.convention.android.library.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = libs.plugins.convention.android.compose.get().pluginId
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("kotlinLibrary") {
            id = libs.plugins.convention.kotlin.library.get().pluginId
            implementationClass = "KotlinLibraryConventionPlugin"
        }
        register("kotlinSerialization") {
            id = libs.plugins.convention.kotlin.serialization.get().pluginId
            implementationClass = "KotlinSerializationConventionPlugin"
        }
        register("androidHilt") {
            id = libs.plugins.convention.android.hilt.get().pluginId
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = libs.plugins.convention.android.room.get().pluginId
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmTest") {
            id = libs.plugins.convention.jvm.test.get().pluginId
            implementationClass = "JvmTestConventionPlugin"
        }
        register("arrow") {
            id = libs.plugins.convention.arrow.get().pluginId
            implementationClass = "ArrowConventionPlugin"
        }
        register("navigation") {
            id = libs.plugins.convention.navigation.get().pluginId
            implementationClass = "NavigationConventionPlugin"
        }
        register("kotlinxDateTime") {
            id = libs.plugins.convention.kotlinx.datetime.get().pluginId
            implementationClass = "KotlinxDateTimeConventionPlugin"
        }
    }
}
