package com.github.iipekolict.knest.injectors

abstract class SuspendAnnotationInjector<A : Annotation, P> : AnnotationInjector<A>() {

    abstract suspend fun inject(): P
}