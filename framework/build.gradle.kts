val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val swaggerVersion: String by project
val GROUP: String by project
val VERSION_NAME: String by project

plugins {
    `java-library`
    kotlin("jvm") version "1.8.21"
    id("com.vanniktech.maven.publish") version "0.25.2"
}

group = GROUP.replace('-', '_')
version = VERSION_NAME

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")

    implementation ("io.github.smiley4:ktor-swagger-ui:$swaggerVersion")
}