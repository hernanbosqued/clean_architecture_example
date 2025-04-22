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
            implementation(libs.koin.core)
            api(project(":frontend:repository:impl"))
        }

        jvmMain.dependencies {
            // JVM-specific dependencies if needed
        }

        wasmJsMain.dependencies {
            // WasmJs-specific dependencies if needed
        }
    }
}
