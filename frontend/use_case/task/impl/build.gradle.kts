@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "hernanbosqued.frontend.use_case.task"

kotlin {
    jvm()

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)

            implementation(project(":domain"))
            implementation(project(":frontend:use_case:task:public"))
        }
    }
}
