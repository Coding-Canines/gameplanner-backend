plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.exposed)
}

group = "com.codingcanines"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.cio.EngineMain"
}

ktor {
    openApi {
        enabled = true
        codeInferenceEnabled = true
    }
}

exposed {
    migrations {
        tablesPackage.set("com.codingcanines.database.tables")
        testContainersImageName.set("postgres:15-alpine")
        fileDirectory.set(layout.projectDirectory.dir("src/main/resources/db/migration"))
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    // Core
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.config.yaml)

    // Plugins
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.di)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.routing.openapi)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.request.validation)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.logback.classic)

    // Database
    implementation(libs.r2dbc.postgresql)
    implementation(libs.jdbc.postgres)
    implementation(libs.exposed.core)
    implementation(libs.exposed.r2dbc)
    implementation(libs.exposed.kotlin.datetime)

    // Migrations
    implementation(libs.flyway.core)
    implementation(libs.flyway.postgres)

    // Utilities
    implementation(libs.bcrypt)
    implementation(libs.kotlinx.datetime)
    implementation(libs.konform)

    // Testing
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
