import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.libs

/**
 * Convention plugin for Android Compose.
 * This plugin applies common configuration for Compose in Android modules.
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply the Kotlin Compose plugin
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            // Configure Android
            extensions.findByType(CommonExtension::class.java)?.apply {
                buildFeatures {
                    compose = true
                }
            }

            // Add Compose dependencies
            dependencies {
                add("implementation", libs.findLibrary("androidx.activity.compose").get())
                add("implementation", libs.findLibrary("androidx.ui").get())
                add("implementation", libs.findLibrary("androidx.ui.graphics").get())
                add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())
                add("implementation", libs.findLibrary("androidx.material3").get())
                add("implementation", libs.findLibrary("androidx.material3.window.size").get())
                add("implementation", libs.findLibrary("androidx.foundation").get())
                add("implementation", libs.findLibrary("kotlinx.collections.immutable").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtime.compose").get())

                add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
                add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
            }
        }
    }
}
