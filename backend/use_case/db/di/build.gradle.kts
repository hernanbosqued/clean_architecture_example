plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case.db"

dependencies {
    implementation(libs.koin.core)

    implementation(project(":backend:use_case:db:public"))
    implementation(project(":backend:use_case:db:impl"))
    implementation(project(":domain"))

    testRuntimeOnly(libs.kotlin.test.junit)
}
