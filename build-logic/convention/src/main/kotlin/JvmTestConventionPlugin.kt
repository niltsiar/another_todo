import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import utils.libs

/**
 * Convention plugin for configuring JVM tests.
 * This plugin applies common test configuration to all modules.
 */
class JvmTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply the test-logger plugin
            pluginManager.apply("com.adarshr.test-logger")

            // Configure test tasks
            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
                // Force tests to run every time
                outputs.upToDateWhen { false }
            }

            // Add common test dependencies
            dependencies {
                add("testImplementation", libs.findLibrary("turbine").get())
                add("testImplementation", libs.findLibrary("mockk").get())
                add("testImplementation", libs.findLibrary("kotest.runner").get())
                add("testImplementation", libs.findLibrary("kotest.assertions").get())
                add("testImplementation", libs.findLibrary("kotest.property").get())
                add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
            }
        }
    }
}
