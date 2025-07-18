import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.libs

/**
 * Convention plugin for Arrow.
 * This plugin applies common configuration for Arrow in all modules.
 */
class ArrowConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Add Arrow dependencies
            dependencies {
                add("implementation", libs.findLibrary("arrow.core").get())
                add("implementation", libs.findLibrary("arrow.fx.coroutines").get())
            }
        }
    }
}
