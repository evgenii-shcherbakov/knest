package io.github.evgenii_shcherbakov.knest.injectors

abstract class SuspendAnnotationInjector<A : Annotation, P> : AnnotationInjector<A>() {

    abstract suspend fun inject(): P
}