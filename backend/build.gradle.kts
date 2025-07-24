plugins {
    kotlin("jvm")
    application
}

group = "hernanbosqued.backend"

application {
    mainClass.set("hernanbosqued.backend.MainKt")
}

dependencies {
    api(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
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

distributions {
    main { // O el nombre de tu distribución si es diferente
        contents {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            // Aquí puedes refinar más si es necesario, por ejemplo:
            // from(sourceSets.main.output)
            // into("lib") {
            //     from(configurations.runtimeClasspath)
            // }
        }
    }
}

tasks.jar {
    archiveBaseName.set(project.name)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "hernanbosqued.backend.MainKt"
    }

    // Incluye las clases compiladas y recursos de este módulo
    from(sourceSets.main.get().output)

    // Incluye todas las dependencias de runtime
    from({
        configurations.runtimeClasspath.get()
            .filter { it.exists() }
            .map { if (it.isDirectory) it else zipTree(it) }
    })

    destinationDirectory.set(file("output"))
}

tasks.named<JavaExec>("run") {
    dependsOn(":domain:generateConstants")
    args("output/database/database.db")
}
