plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case.auth"

dependencies {
    testImplementation(libs.kotlin.test.junit)

    implementation(project(":domain"))
}
