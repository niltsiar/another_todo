import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.libs

/**
 * Convention plugin for Navigation.
 * This plugin applies common configuration for Navigation in Android modules.
 */
class NavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Add Navigation dependencies
            dependencies {
                add("implementation", libs.findLibrary("androidx.navigation").get())
            }
        }
    }
}
