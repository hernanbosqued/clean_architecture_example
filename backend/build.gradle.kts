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
    implementation(libs.ktor.serialization)
    implementation("ch.qos.logback:logback-classic:1.5.18")
    // koin
    implementation(libs.koin.ktor)

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
