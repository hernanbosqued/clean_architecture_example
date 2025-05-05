plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.auth_api_gateway.google"

dependencies {
    implementation(libs.koin.core)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.http)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":backend:auth_api_gateway:google:impl"))
    implementation(project(":domain"))
}
