import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

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
        }
    }
}
