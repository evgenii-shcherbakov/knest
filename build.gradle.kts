val projectVersion: String by project
val projectGroup: String by project

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.7.10"
}

allprojects {
    group = projectGroup
    version = projectVersion

    repositories {
        mavenCentral()

        maven {
            setUrl("https://jitpack.io")
        }
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    java {
        withJavadocJar()
        withSourcesJar()
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
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
                }
            }
        }
    }

    tasks.javadoc {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }
}