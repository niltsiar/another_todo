import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.libs

/**
 * Convention plugin for Kotlin serialization.
 * This plugin applies the Kotlin serialization plugin and adds the serialization dependency.
 */
class KotlinSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply the Kotlin serialization plugin
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
            
            // Add serialization dependency
            dependencies {
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
            }
        }
    }
}
