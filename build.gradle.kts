val libraryVersion: String by project
val libraryGroup: String by project

plugins {
    kotlin("jvm") version "1.7.10"
}

allprojects {
    group = libraryGroup
    version = libraryVersion

    repositories {
        mavenCentral()

        maven {
            setUrl("https://jitpack.io")
        }
    }
}