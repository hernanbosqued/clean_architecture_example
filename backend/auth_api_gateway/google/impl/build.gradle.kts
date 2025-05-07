plugins {
    kotlin("jvm")
    alias(libs.plugins.serialization)
}

group = "hernanbosqued.backend.auth_api_gateway.google"

dependencies {
    api(libs.ktor.client.core)
    api(libs.kotlinx.serialization.core)
    api(project(":domain"))

    implementation(libs.ktor.http)
    implementation(libs.ktor.utils)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.server.auth.jwt)

    testRuntimeOnly(libs.kotlin.test.junit)
}
