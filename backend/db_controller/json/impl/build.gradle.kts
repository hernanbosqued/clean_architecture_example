plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.db_controller.json"
dependencies {
    testImplementation(libs.kotlin.test.junit)
    implementation(libs.gson)
    implementation(project(":domain"))
}
