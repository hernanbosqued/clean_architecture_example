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

    implementation(libs.warrenstrange.googleauth)
    implementation(libs.zxing.javase)
    implementation(libs.zxing.core)

    testImplementation(libs.junit)
    testRuntimeOnly(libs.kotlin.test.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.ktor.server.content.negotiation)
    testImplementation(libs.ktor.serialization.json)
    testImplementation(libs.ktor.server.tests.jvm)
    testImplementation(libs.ktor.client.core)
}
