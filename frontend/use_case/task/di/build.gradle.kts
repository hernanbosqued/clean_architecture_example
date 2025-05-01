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
            implementation(libs.koin.core)
            api(project(":domain"))
            api(project(":frontend:use_case:task:public"))
            implementation(project(":frontend:use_case:task:impl"))
        }

        jvmMain.dependencies {
        }

        wasmJsMain.dependencies {
        }
    }
}
