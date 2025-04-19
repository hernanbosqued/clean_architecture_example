plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "hernanbosqued.backend.domain"
version = "1.0"

kotlin {
    jvm()
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
        }

        jvmMain.dependencies {
        }

        wasmJsMain.dependencies {
        }
    }
}