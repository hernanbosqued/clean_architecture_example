plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.controller"
version = "1.0"

dependencies {
    //koin
    implementation(libs.koin.core)

    implementation(project(":backend:presenter:di"))
    implementation(project(":backend:controller:impl"))
    implementation(project(":backend:domain"))
}