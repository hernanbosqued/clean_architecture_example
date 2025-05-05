@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "hernanbosqued.backend.presenter"

kotlin {
    jvm()

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":domain"))
        }
    }
}
