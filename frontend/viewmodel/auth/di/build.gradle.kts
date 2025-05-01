@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "hernanbosqued.frontend.viewmodel.auth"

kotlin {
    jvm("desktop")

    wasmJs {
        browser()
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(libs.koin.core)
            api(project(":frontend:viewmodel:auth:public"))
            implementation(project(":frontend:viewmodel:auth:impl"))
            implementation(project(":frontend:use_case:auth:public"))
        }

        desktopMain.dependencies {
        }

        wasmJsMain.dependencies {
        }
    }
}
