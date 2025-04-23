plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.db_controller"


dependencies {
    // koin
    implementation(libs.koin.core)

    implementation(project(":backend:presenter:di"))
    implementation(project(":backend:db_controller:impl"))
    implementation(project(":backend:domain"))
}
