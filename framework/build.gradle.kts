val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val swaggerVersion: String by project

plugins {
    kotlin("jvm") version "1.7.10"
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")

    implementation ("io.github.smiley4:ktor-swagger-ui:$swaggerVersion")

    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
}