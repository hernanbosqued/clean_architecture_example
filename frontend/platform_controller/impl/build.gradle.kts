@file:OptIn(ExperimentalWasmDsl::class)

import org.gradle.kotlin.dsl.project
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
            implementation(libs.ktor.http)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            implementation(libs.multiplatform.setting)
            implementation(libs.kotlinx.coroutines.core)
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
