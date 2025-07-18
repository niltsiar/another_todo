import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.libs

/**
 * Convention plugin for Android Hilt.
 * This plugin applies common configuration for Hilt in Android modules.
 */
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply the Hilt plugin
            pluginManager.apply("com.google.dagger.hilt.android")
            // Apply the KSP plugin
            pluginManager.apply("com.google.devtools.ksp")
            
            // Add Hilt dependencies
            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
                add("implementation", libs.findLibrary("hilt.navigation.compose").get())
            }
        }
    }
}
