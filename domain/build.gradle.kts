@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompile

plugins {
    alias(libs.plugins.serialization)
    alias(libs.plugins.kotlinMultiplatform)
}

group = "hernanbosqued.backend.domain"

kotlin {
    jvm()
    wasmJs {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir(layout.buildDirectory.dir("generated/source/constants/kotlin"))

            dependencies {
                implementation(libs.kotlinx.serialization.json)
            }
        }
    }
}

tasks.register("generateConstants") {
    group = "build setup"
    description = "Generates Constants.kt from gradle properties"

    val outputDir = layout.buildDirectory.dir("generated/source/constants/kotlin")
    val outputFile = outputDir.map { it.file("Constants.kt") }

    outputs.dir(outputDir)
    outputs.file(outputFile).withPropertyName("outputFile")

    doLast {
        val googleClient = providers.gradleProperty("COMMON.GOOGLE.CLIENT").get()
        val goggleSecret = providers.gradleProperty("COMMON.GOOGLE.SECRET").get()
        val googleJwks = providers.gradleProperty("COMMON.GOOGLE.JWKS").get()
        val googleIssuer = providers.gradleProperty("COMMON.GOOGLE.ISSUER").get()
        val desktopRedirectUrl = providers.gradleProperty("COMMON.DESKTOP.REDIRECT_URL").get()
        var webRedirectUrl: String
        var apiUrl: String

        when (currentBuildVariant()) {
            "remote" -> {
                apiUrl = providers.gradleProperty("REMOTE.API_URL").get()
                webRedirectUrl = providers.gradleProperty("REMOTE.WEB.REDIRECT_URL").get()
            }

            else -> { // LOCAL
                apiUrl = providers.gradleProperty("LOCAL.API_URL").get()
                webRedirectUrl = providers.gradleProperty("LOCAL.WEB.REDIRECT_URL").get()
            }
        }

        outputFile.get().asFile.parentFile.mkdirs()

        outputFile.get().asFile.writeText(
            """
            package hernanbosqued.constants

            object Constants {
                const val GOOGLE_SECRET = "$goggleSecret"
                const val GOOGLE_CLIENT = "$googleClient"
                const val GOOGLE_JWKS = "$googleJwks"
                const val GOOGLE_ISSUER = "$googleIssuer"
                const val DESKTOP_REDIRECT_URL = "$desktopRedirectUrl"
                const val WEB_REDIRECT_URL = "$webRedirectUrl"
                const val API_URL = "$apiUrl"
                
            }
            """.trimIndent(),
        )
        println("Generated Constants.kt")
    }
}

tasks.named("generateConstants") {
    outputs.upToDateWhen { false }
}

tasks.withType<AbstractKotlinCompile<*>>().configureEach {
    dependsOn(tasks.named("generateConstants"))
}

tasks.named("runKtlintCheckOverCommonMainSourceSet") {
    dependsOn(tasks.named("generateConstants"))
}

private fun currentBuildVariant(): String {
    val variants = setOf("local", "remote")
    val variant = System.getProperty("variant")
    println("***** El variant pasado por parametro es: $variant *****")

    return variant.takeIf { it in variants } ?: "local"
}
