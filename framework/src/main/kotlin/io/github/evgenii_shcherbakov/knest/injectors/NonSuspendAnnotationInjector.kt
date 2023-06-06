package io.github.evgenii_shcherbakov.knest.injectors

abstract class NonSuspendAnnotationInjector<A : Annotation, P> : AnnotationInjector<A>() {

    abstract fun inject(): P
}