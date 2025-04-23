plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend.use_case"


dependencies {
    testImplementation(libs.kotlin.test.junit)

    implementation(libs.koin.core)
    api(project(":backend:domain"))
}
