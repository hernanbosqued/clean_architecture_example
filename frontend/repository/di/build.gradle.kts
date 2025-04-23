@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "hernanbosqued.frontend.repository"


kotlin {
    jvm()

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            api(project(":frontend:repository:impl"))
        }

        jvmMain.dependencies {
            // JVM-specific dependencies if needed
        }

        wasmJsMain.dependencies {
            // WasmJs-specific dependencies if needed
        }
    }
}
