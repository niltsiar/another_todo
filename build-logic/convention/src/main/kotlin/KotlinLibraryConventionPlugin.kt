import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import utils.libs

/**
 * Convention plugin for Kotlin library modules.
 * This plugin applies common configuration for Kotlin JVM modules.
 */
class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply the Kotlin JVM plugin
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            // Apply the JVM test convention plugin
            pluginManager.apply("dev.niltsiar.anothertodo.jvm.test")

            // Configure Java
            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            // Configure Kotlin
            extensions.configure<KotlinJvmProjectExtension> {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }

            // Add common dependencies
            dependencies {
                add("implementation", libs.findLibrary("kotlinx.coroutines.core").get())
            }
        }
    }
}
