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

    // Agregar al TOML
    // Para la lógica de Google Authenticator (TOTP)
    implementation("com.warrenstrange:googleauth:1.5.0")

    // Para generar los códigos QR (ZXing)
    implementation("com.google.zxing:javase:3.5.1")
    implementation("com.google.zxing:core:3.5.1")

    testRuntimeOnly(libs.kotlin.test.junit)
}
