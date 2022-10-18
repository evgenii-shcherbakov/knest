<p align="center">
    <a href="http://nestjs.com/" target="blank">
        <img src="assets/img/knest-logo.png" width="120" alt="Nest Logo" />
    </a>
</p>

<p align="center">
    A progressive <a href="https://kotlinlang.org/" target="_blank">Kotlin</a> 
    framework based on <a href="https://ktor.io/" target="_blank">Ktor</a> 
    and modeled after <a href="https://nestjs.com/" target="_blank">NestJS</a> 
    for building efficient and scalable server-side applications.
</p>

<p align="center">
    <a href="https://jitpack.io/#IIPEKOLICT/knest" target="_blank">
        <img src="https://jitpack.io/v/IIPEKOLICT/knest.svg" alt="JitPack version" />
    </a>
    <a href="https://raw.githubusercontent.com/IIPEKOLICT/knest/main/LICENSE" target="_blank">
        <img src="https://img.shields.io/badge/license-MIT-green.svg" alt="Package License" />
    </a>
</p>

## Description

KNest is a framework for building efficient, 
scalable <a href="https://kotlinlang.org/" target="_blank">Kotlin</a> server-side applications. 
It combines elements of OOP (Object Oriented Programming), 
FP (Functional Programming), and FRP (Functional Reactive Programming).

Under the hood, KNest makes use of <a href="https://ktor.io/" target="_blank">Ktor</a>, 
allowing for easy use of the myriad third-party plugins which are available.

## Philosophy

This framework aims to combine the ability to write as beautifully as in 
<a href="https://nestjs.com/" target="_blank">NestJS</a>, combine convenient approaches from
<a href="https://spring.io/projects/spring-boot" target="_blank">Spring Boot</a> and at the same time 
use fast and lightweight <a href="https://ktor.io/" target="_blank">Ktor</a> on
<a href="https://kotlinlang.org/" target="_blank">Kotlin</a> under the hood.

## Getting started

- Create new Ktor Server application
- Add JitPack repository to project build.gradle.kts file
```kotlin
repositories {
    // ...

    maven {
        setUrl("https://jitpack.io")
    }
}
```
- Add framework dependency to ktor app build.gradle.kts
```kotlin
dependencies {
    // ...
    implementation("com.github.IIPEKOLICT:knest:$version")
}
```
- Install and configure KNest plugin to your application
```kotlin
package com.example

import com.github.iipekolict.knest.KNest
import io.ktor.http.*
import io.ktor.server.netty.EngineMain
import io.ktor.server.application.*
import com.example.controllers.MainController

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(KNest) {
        setControllers(MainController())
    }
}
```

You can see more examples how to use it in 
<a href="https://github.com/IIPEKOLICT/knest/tree/main/preview" target="_blank">preview module</a>.

## License

KNest is [MIT licensed](LICENSE).