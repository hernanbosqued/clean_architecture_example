plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend"
version = "1.0"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    //ktor
    implementation (libs.ktor.server.core)
    implementation (libs.ktor.server.netty)
    implementation (libs.ktor.server.content)
    implementation (libs.ktor.server.serialization)

    //koin
    implementation(libs.koin.ktor)

    implementation(project(":presenter:di"))
    implementation(project(":controller:di"))
    implementation(project(":use_case:di"))
}