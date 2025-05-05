plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.presenter"

dependencies {
    api(project(":domain"))
    api(project(":backend:presenter:public"))
    api(project(":backend:use_case:db:public"))
    api(project(":backend:use_case:auth:public"))

    testImplementation(libs.junit)
    testRuntimeOnly(libs.kotlin.test.junit)
}
