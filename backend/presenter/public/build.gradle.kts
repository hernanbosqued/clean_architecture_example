plugins {
    kotlin("jvm")
    alias(libs.plugins.serialization)
}

group = "hernanbosqued.backend.presenter"
version = "1.0"

dependencies {
    testImplementation(libs.kotlin.test.junit)

    implementation(libs.kotlinx.serialization.json)
    implementation(project(":backend:domain"))
}