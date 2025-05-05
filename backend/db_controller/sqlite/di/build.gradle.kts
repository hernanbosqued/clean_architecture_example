plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.db_controller.sqlite"

dependencies {
    implementation(libs.koin.core)
    implementation(libs.sqdelight)

    implementation(project(":backend:db_controller:sqlite:impl"))
    implementation(project(":domain"))
}
