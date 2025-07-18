import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.libs

/**
 * Convention plugin for Kotlinx DateTime.
 * This plugin applies common configuration for Kotlinx DateTime in all modules.
 */
class KotlinxDateTimeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Add Kotlinx DateTime dependencies
            dependencies {
                add("implementation", libs.findLibrary("kotlinx.datetime").get())
            }
        }
    }
}
