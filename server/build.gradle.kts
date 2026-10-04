plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
}

group = "com.example.template"
version = "1.0.0"
application {
    mainClass = "com.example.template.ApplicationKt"
}

dependencies {
    api(projects.core)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(libs.ktor.serverConfigYaml)
    implementation(libs.ktor.serverContentNegotiation)
	testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}
