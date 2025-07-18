import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.libs

/**
 * Convention plugin for Android Room.
 * This plugin applies common configuration for Room in Android modules.
 */
class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply the KSP plugin
            pluginManager.apply("com.google.devtools.ksp")
            
            // Add Room dependencies
            dependencies {
                add("implementation", libs.findLibrary("room.runtime").get())
                add("ksp", libs.findLibrary("room.compiler").get())
                add("implementation", libs.findLibrary("room.ktx").get())
            }
        }
    }
}
