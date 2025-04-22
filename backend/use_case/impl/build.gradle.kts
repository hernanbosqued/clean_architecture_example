plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case"
version = "1.0"

dependencies {
    testImplementation(libs.kotlin.test.junit)

    implementation(libs.koin.core)
    api(project(":backend:use_case:public"))
    api(project(":backend:domain"))
}
