plugins {
    kotlin("jvm")
    id("app.cash.sqldelight") version "2.0.2"
}

group = "hernanbosqued.backend.db_controller"

dependencies {
    api(project(":domain"))

    implementation(libs.sqdelight.driver)
    implementation(libs.sqdelight)

    testImplementation(libs.junit)
    testRuntimeOnly(libs.kotlin.test.junit)
}

sqldelight {
    databases {
        create("ServerDatabase") {
            packageName.set("hernanbosqued.backend.db")
        }
    }
}
