plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.json_controller"

dependencies {
    // koin
    implementation(libs.koin.core)

    implementation(project(":backend:presenter:di"))
    implementation(project(":backend:json_controller:impl"))
    implementation(project(":backend:domain"))
}
