plugins {
    kotlin("jvm")
    id("app.cash.sqldelight") version "2.0.2"
}

group = "hernanbosqued.backend.db_controller"

dependencies {
    testImplementation(libs.kotlin.test.junit)
    implementation(libs.gson)
    implementation(project(":domain"))
    implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
}

sqldelight {
    databases {
        create("ServerDatabase") {
            packageName.set("hernanbosqued.backend.db")
        }
    }
}
