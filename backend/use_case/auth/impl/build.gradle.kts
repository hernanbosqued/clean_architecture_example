plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case.auth"

dependencies {
    api(project(":backend:use_case:auth:public"))
    api(project(":domain"))
    implementation(libs.warrenstrange.googleauth)

    testRuntimeOnly(libs.kotlin.test.junit)
}
