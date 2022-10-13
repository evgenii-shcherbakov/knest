package com.github.iipekolict.knest.builders.injectors

abstract class SuspendAnnotationInjector<A : Annotation, P> : AnnotationInjector<A, P>() {

    abstract suspend fun inject(): P
}