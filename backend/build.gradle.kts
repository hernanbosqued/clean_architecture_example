plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend"


dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // ktor
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content)
    implementation(libs.ktor.server.serialization)
    implementation("ch.qos.logback:logback-classic:1.5.18")
    // koin
    implementation(libs.koin.ktor)

    implementation(project(":backend:presenter:di"))
    implementation(project(":backend:db_controller:di"))
    implementation(project(":backend:use_case:di"))
}
