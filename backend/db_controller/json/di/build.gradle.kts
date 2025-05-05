plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.db_controller.json"

dependencies {
    implementation(libs.koin.core)

    implementation(project(":backend:db_controller:json:impl"))
    implementation(project(":domain"))
}
