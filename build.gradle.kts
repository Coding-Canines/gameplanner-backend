plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
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

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.di)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.bcrypt)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.r2dbc.postgresql)
    implementation(libs.exposed.core)
    implementation(libs.exposed.r2dbc)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.cio)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.routing.openapi)
    implementation(libs.ktor.server.cors)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
