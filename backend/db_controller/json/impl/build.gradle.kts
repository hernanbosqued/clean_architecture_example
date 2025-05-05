plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.db_controller.json"
dependencies {
    implementation(libs.gson)
    api(project(":domain"))

    testImplementation(libs.junit)
    testRuntimeOnly(libs.kotlin.test.junit)
}
