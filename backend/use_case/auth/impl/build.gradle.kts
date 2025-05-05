plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case.auth"

dependencies {
    api(project(":backend:use_case:auth:public"))
    api(project(":domain"))

    testRuntimeOnly(libs.kotlin.test.junit)
}
