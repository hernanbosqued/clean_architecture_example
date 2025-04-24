plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.presenter"

dependencies {
    testImplementation(libs.kotlin.test.junit)

    implementation(libs.koin.core)
    implementation(project(":backend:use_case:public"))
    implementation(project(":backend:presenter:impl"))
    api(project(":backend:presenter:public"))
}
