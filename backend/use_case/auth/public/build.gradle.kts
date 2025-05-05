plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case.auth"

dependencies {
    api(project(":domain"))
    testRuntimeOnly(libs.kotlin.test.junit)
}
