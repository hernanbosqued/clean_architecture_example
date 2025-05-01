plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.db_controller.json"

dependencies {
    // koin
    implementation(libs.koin.core)

    implementation(project(":backend:presenter:di"))
    implementation(project(":backend:db_controller:json:impl"))
    implementation(project(":domain"))
}
