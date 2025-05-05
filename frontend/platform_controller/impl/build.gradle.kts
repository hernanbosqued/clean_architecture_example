@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
}

group = "hernanbosqued.frontend.platform_controller"
kotlin {
    jvm("desktop")

    wasmJs {
        browser()
    }
    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.http)
            implementation(libs.multiplatform.setting)
            implementation(libs.kotlinx.serialization.json)

            implementation(project(":frontend:use_case:auth:public"))
            implementation(project(":domain"))
        }
        desktopMain.dependencies {
            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.netty)
        }

        wasmJsMain.dependencies {
            implementation(libs.kotlinx.browser)
        }
    }
}
