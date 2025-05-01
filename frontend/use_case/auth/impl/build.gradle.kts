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
        val desktopMain by getting

        commonMain.dependencies {
            implementation(libs.koin.core)
            api(project(":frontend:use_case:auth:public"))
            api(project(":frontend:repository:public"))
            api(project(":backend:presenter:public"))
            api(project(":domain"))

            // ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.serialization.kotlinx.json)
        }

        jvmTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }

        desktopMain.dependencies {
            implementation(libs.ktor.server.core)
            implementation(libs.java.jwt)
            implementation(libs.ktor.server.netty)
        }

        wasmJsMain.dependencies {
            implementation(libs.kotlinx.browser)
        }
    }
}
