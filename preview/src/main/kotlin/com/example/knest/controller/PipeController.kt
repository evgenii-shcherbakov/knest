package com.example.knest.controller

import io.github.evgenii_shcherbakov.knest.annotations.classes.Controller
import io.github.evgenii_shcherbakov.knest.annotations.methods.Get
import io.github.evgenii_shcherbakov.knest.annotations.properties.Param

@Controller("pipe")
class PipeController {

    class Response<T>(val id: T)

    @Get("{id}/int")
    suspend fun getParamInt(@Param("id") id: Int): Response<Int> {
        return Response(id)
    }

    @Get("{id}/float")
    suspend fun getParamFloat(@Param("id") id: Float): Response<Float> {
        return Response(id)
    }

    @Get("{id}/double")
    suspend fun getParamDouble(@Param("id") id: Double): Response<Double> {
        return Response(id)
    }
}