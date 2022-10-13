package com.github.iipekolict.knest.builders.injectors

abstract class NonSuspendAnnotationInjector<A : Annotation, P> : AnnotationInjector<A, P>() {

    abstract fun inject(): P
}