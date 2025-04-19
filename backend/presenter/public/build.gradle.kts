plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
}

group = "hernanbosqued.backend.presenter"
version = "1.0"

kotlin {
    jvm()

    wasmJs {
        browser()
    }

    sourceSets {
        jvmTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }

        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            api(project(":backend:domain"))
        }

        jvmMain.dependencies {
        }

        wasmJsMain.dependencies {
        }
    }

}