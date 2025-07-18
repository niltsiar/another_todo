// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.test.logger) apply false
}

subprojects {
    plugins.withId("com.adarshr.test-logger") {
        configure<com.adarshr.gradle.testlogger.TestLoggerExtension> {
            theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
            showExceptions = true
            showStackTraces = true
            showFullStackTraces = true
            showCauses = true
            slowThreshold = 2000
            showSummary = true
            showSimpleNames = false
            showPassed = true
            showSkipped = true
            showFailed = true
            showStandardStreams = true
            showPassedStandardStreams = true
            showSkippedStandardStreams = true
            showFailedStandardStreams = true
        }
    }
}
