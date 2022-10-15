package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Get
import com.github.iipekolict.knest.annotations.properties.Param

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