plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case.auth"

dependencies {
    implementation(libs.koin.core)

    implementation(project(":backend:use_case:auth:impl"))
    implementation(project(":backend:use_case:auth:public"))
    implementation(project(":domain"))
}
