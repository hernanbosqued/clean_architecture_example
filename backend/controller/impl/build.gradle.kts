plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.controller"
version = "1.0"

dependencies {
    testImplementation(libs.kotlin.test.junit)
    implementation(libs.gson)
    implementation(project(":backend:domain"))
}