val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val swaggerVersion: String by project

val libraryVersion: String by project
val libraryGroup: String by project
val libraryArtifactId: String by project

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.7.10"
}

repositories {
    mavenCentral()

    maven {
        setUrl("https://jitpack.io")
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")

    implementation ("io.github.smiley4:ktor-swagger-ui:$swaggerVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            version = libraryVersion
            group = libraryGroup
            artifactId = libraryArtifactId

            from(components["java"])

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }

                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name.set("KNest")
                description.set("Nest-like framework based on Ktor")
//                url.set("http://www.example.com/library")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("iipekolict")
                        name.set("Evgenii Shcherbakov")
                        email.set("iipekolict@gmail.com")
                    }
                }

//                scm {
//                    connection.set("scm:git:git://example.com/my-library.git")
//                    developerConnection.set("scm:git:ssh://example.com/my-library.git")
//                    url.set("http://example.com/my-library/")
//                }
            }
        }
    }

    repositories {
        maven {
            url = uri("${buildDir}/publishing-repository")
        }
    }
}

//signing {
//    sign(publishing.publications["mavenJava"])
//}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}