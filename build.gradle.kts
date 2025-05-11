plugins {
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "com.autonomousapps.dependency-analysis")

    version = "1.0"

    ktlint {
        filter {
            exclude("**/generated/**")
        }
    }
}
