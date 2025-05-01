plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case.auth"

dependencies {
    testImplementation(libs.kotlin.test.junit)

    implementation(project(":backend:use_case:auth:public"))
    implementation(project(":domain"))
}
