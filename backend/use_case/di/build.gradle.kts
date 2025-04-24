plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case"

dependencies {
    testImplementation(libs.kotlin.test.junit)

    implementation(libs.koin.core)
    api(project(":backend:use_case:public"))
    implementation(project(":backend:use_case:impl"))
    api(project(":backend:domain"))
}
