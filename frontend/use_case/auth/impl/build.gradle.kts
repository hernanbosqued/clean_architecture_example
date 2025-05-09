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
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.http)

            api(project(":frontend:use_case:auth:public"))
            api(project(":domain"))

            implementation("com.google.code.gson:gson:2.10.1") // Añade esto temporalmente
        }
    }
}
