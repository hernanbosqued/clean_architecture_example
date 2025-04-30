plugins {
    kotlin("jvm")
}

group = "hernanbosqued.backend"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // ktor
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content)
    implementation(libs.ktor.server.serialization)
    implementation(libs.java.jwt)
    implementation("ch.qos.logback:logback-classic:1.5.18")
    // koin
    implementation(libs.koin.ktor)

    implementation(project(":backend:presenter:di"))
    implementation(project(":backend:db_controller:di"))
    implementation(project(":backend:use_case:di"))
}

kotlin{
    sourceSets.main {
        kotlin.srcDir(layout.buildDirectory.dir("generated/source/constants/kotlin"))
    }
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

tasks.register("generateConstants") {
    group = "build setup"
    description = "Generates Constants.kt from gradle properties"

    // Define el archivo de salida usando la API lazy de Gradle
    val outputDir = layout.buildDirectory.dir("generated/source/constants/kotlin") // Directorio de salida
    val outputFile = outputDir.map { it.file("Constants.kt") } // Archivo de salida

    // Declara el archivo de salida para que Gradle sepa qué produce esta tarea
    outputs.file(outputFile).withPropertyName("outputFile")

    doLast { // La lógica de lectura y escritura va en doFirst/doLast
        // Accede a la propiedad de forma segura y lazy
        // getOrElse() es más seguro que tu ?: "default_url" porque maneja el caso de propiedad no encontrada explícitamente
        val secretValue = providers.gradleProperty("GOOGLE_SECRET")
            .getOrElse("default_secret_error_if_not_found") // Cambia esto si quieres un valor por defecto diferente o usa .get() para fallar si no existe

        // Asegúrate de que el directorio padre exista antes de escribir
        outputFile.get().asFile.parentFile.mkdirs()

        // Escribe el archivo con un paquete
        outputFile.get().asFile.writeText("""
            package hernanbosqued.backend.config // <-- Define un paquete!

            object Constants {
                // Asegúrate de escapar correctamente las comillas si el secreto las contiene
                const val GOOGLE_SECRET = "$secretValue"
            }
        """.trimIndent())
        println("Generated Constants.kt")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    dependsOn(tasks.named("generateConstants"))
}