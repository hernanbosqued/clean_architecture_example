plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.db_controller.sqlite"

dependencies {
    // koin
    implementation(libs.koin.core)

    implementation(project(":backend:presenter:di"))
    implementation(project(":backend:db_controller:sqlite:impl"))
    implementation(project(":domain"))
}
