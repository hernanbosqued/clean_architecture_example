import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

apply(plugin = "com.autonomousapps.dependency-analysis")

kotlin {
    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "frontend_ui.js"
                devServer =
                    (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                        port = 8082
                        static =
                            (static ?: mutableListOf()).apply {
                                add(project.rootDir.path)
                                add(project.projectDir.path)
                                add(project.projectDir.parent)
                            }
                    }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            runtimeOnly(libs.logback.classic)

            implementation(project(":frontend:platform_controller:di"))

            implementation(project(":frontend:viewmodel:auth:di"))
            implementation(project(":frontend:use_case:auth:di"))

            implementation(project(":frontend:viewmodel:task:di"))
            implementation(project(":frontend:use_case:task:di"))

            implementation(project(":frontend:repository:di"))
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.desktop {
    application {
        mainClass = "hernanbosqued.frontend.ui.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "hernanbosqued.frontend.ui"
            packageVersion = "1.0.0"
        }
    }
}

tasks.register<Copy>("copyWasmDistributionToRoot") {
    dependsOn(tasks.named("wasmJsBrowserDistribution"))
    from(layout.buildDirectory.dir("dist/wasmJs/productionExecutable"))
    into(project.layout.projectDirectory.dir("output"))
}

tasks.named("wasmJsBrowserDistribution") {
    finalizedBy("copyWasmDistributionToRoot")
}
