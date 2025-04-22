plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.presenter"
version = "1.0"

dependencies {
    testImplementation(libs.kotlin.test.junit)

    implementation(project(":backend:domain"))
    implementation(project(":backend:presenter:public"))
    implementation(project(":backend:use_case:public"))
}
