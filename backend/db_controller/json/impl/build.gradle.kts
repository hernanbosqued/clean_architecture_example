plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.json_controller"

dependencies {
    testImplementation(libs.kotlin.test.junit)
    implementation(libs.gson)
    implementation(project(":domain"))
}
