plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.presenter"

dependencies {
    testImplementation(libs.kotlin.test.junit)

    implementation(project(":domain"))
    implementation(project(":backend:presenter:public"))
    implementation(project(":backend:use_case:db:public"))
    implementation(project(":backend:use_case:auth:public"))
}
