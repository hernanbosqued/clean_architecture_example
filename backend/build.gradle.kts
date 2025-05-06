plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend"

dependencies {
    api(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.http)
    implementation(libs.ktor.utils)

    implementation(libs.kotlinx.serialization.json)
    runtimeOnly(libs.logback.classic)

    implementation(project(":domain"))
    implementation(project(":backend:presenter:public"))
    implementation(project(":backend:presenter:di"))
    implementation(project(":backend:db_controller:sqlite:di"))
    implementation(project(":backend:auth_api_gateway:google:di"))
    implementation(project(":backend:use_case:db:di"))
    implementation(project(":backend:use_case:auth:di"))
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "hernanbosqued.backend.MainKt"
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    from(sourceSets.main.get().output)

    destinationDirectory.set(file("output"))
}
