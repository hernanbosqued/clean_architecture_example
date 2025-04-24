@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
}

group = "hernanbosqued.backend.presenter"

kotlin {
    jvm()

    wasmJs {
        browser()
    }

    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            api(project(":backend:domain"))
        }

        jvmMain.dependencies {
        }

        wasmJsMain.dependencies {
        }
    }
}
