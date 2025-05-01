plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case.db"

dependencies {
    testImplementation(libs.kotlin.test.junit)

    implementation(libs.koin.core)
    api(project(":domain"))
}
