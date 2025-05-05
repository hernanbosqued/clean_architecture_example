plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case.db"

dependencies {
    api(project(":backend:use_case:db:public"))
    api(project(":domain"))

    testRuntimeOnly(libs.kotlin.test.junit)
}
