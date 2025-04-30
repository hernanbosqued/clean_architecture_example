@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "hernanbosqued.frontend.use_case.auth"

kotlin {
    jvm("desktop")

    wasmJs {
        browser()
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            api(project(":frontend:use_case:auth:public"))
            implementation(project(":frontend:use_case:auth:impl"))
            implementation(project(":backend:domain"))
        }
    }
}
