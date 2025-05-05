@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "hernanbosqued.frontend.platform_controller"

kotlin {
    jvm("desktop")

    wasmJs {
        browser()
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.multiplatform.setting)
            implementation(libs.kotlinx.serialization.json)

            implementation(project(":frontend:platform_controller:impl"))
            implementation(project(":frontend:use_case:auth:public"))
            implementation(project(":domain"))
        }

        wasmJsMain.dependencies {
            implementation(libs.kotlinx.browser)
        }
    }
}
