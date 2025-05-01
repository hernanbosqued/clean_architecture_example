@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "hernanbosqued.frontend.use_case.auth"

kotlin {
    jvm()

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":domain"))
            implementation(project(":backend:presenter:public"))
            implementation(project(":frontend:repository:public"))
            implementation(libs.ktor.http)
        }

        jvmMain.dependencies {
        }

        wasmJsMain.dependencies {
        }
    }
}
