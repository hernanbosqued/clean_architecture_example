plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.presenter"

dependencies {
    implementation(libs.koin.core)

    implementation(project(":backend:use_case:auth:public"))
    implementation(project(":backend:use_case:db:public"))
    implementation(project(":backend:presenter:impl"))
    implementation(project(":backend:presenter:public"))

    testRuntimeOnly(libs.kotlin.test.junit)
}
