plugins {
    kotlin("jvm") version "1.8.21"
}

allprojects {
    repositories {
        mavenCentral()

        maven {
            setUrl("https://jitpack.io")
        }
    }
}
