plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "hernanbosqued.frontend.repository"
version = "1.0"

kotlin {
    jvm()

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization.kotlinx.json)
            // implementation(project(":backend:domain"))
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp) // Add this line for JVM target
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js) // Basic client for Wasm
        }

    }
}