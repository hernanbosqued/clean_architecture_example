import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.TargetConfigDsl
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.Properties

plugins {
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "frontend.ui"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "frontend_ui.js"
                devServer =
                    (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                        static =
                            (static ?: mutableListOf()).apply {
                                // Serve sources to debug inside browser
                                add(rootDirPath)
                                add(projectDirPath)
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
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(project(":frontend:repository:di"))
            implementation(project(":backend:presenter:public"))
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }

    buildkonfig {
        project.extra.set("buildkonfig.flavor", currentBuildVariant())

        packageName = "hernanbosqued.frontend.buildconfig"

        defaultConfigs {
            configsFromProperties("local.properties")
        }

        defaultConfigs("remote") {
            configsFromProperties("remote.properties")
        }
    }
}

private fun currentBuildVariant(): String {
    val variants = setOf("local", "remote")
    return System.getenv()["variant"].toString().takeIf { it in variants } ?: "local"
}

private fun TargetConfigDsl.configsFromProperties(file: String) {
    val properties = Properties().apply {
        val propertiesFile = rootProject.layout.projectDirectory.file("config/$file").asFile
        load(propertiesFile.inputStream())
    }

    properties.stringPropertyNames()
        .forEach { key ->
            field(key.asConfigKey(), properties.getProperty(key))
        }
}

private fun String.asConfigKey() = this.split(".", "-")
    .mapIndexed { index: Int, s: String -> if (index == 0) s else s.uppercaseFirstChar() }
    .joinToString("")

private fun <T> TargetConfigDsl.field(key: String, value: T) {
    val spec = when (value) {
        is String -> FieldSpec.Type.STRING
        is Int -> FieldSpec.Type.INT
        is Float -> FieldSpec.Type.FLOAT
        is Long -> FieldSpec.Type.LONG
        is Boolean -> FieldSpec.Type.BOOLEAN
        else -> error("Unsupported build config value '$value' for '$key'")
    }

    buildConfigField(spec, key, value.toString().trim().removeSurrounding("\""))
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