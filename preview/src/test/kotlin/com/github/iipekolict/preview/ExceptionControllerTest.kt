package com.github.iipekolict.preview

import com.github.iipekolict.preview.controller.ExceptionController
import com.github.iipekolict.preview.dtos.ExceptionDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import io.ktor.server.testing.*
import io.ktor.test.dispatcher.*
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ExceptionControllerTest {

    lateinit var httpClient: HttpClient

    private fun testException(
        path: String,
        expectedResponse: ExceptionDto
    ) {
        return testSuspend {
            httpClient.get("/exception/$path").apply {
                val response: ExceptionDto = body()

                assertNotNull(response, "Response shouldn't be empty")
                assertTrue("$path response should match") { response == expectedResponse }
            }
        }
    }

    @Test
    fun testExceptions() = testApplication {
        httpClient = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        testException(
            path = "knest",
            expectedResponse = ExceptionDto(
                method = "GET",
                code = "framework-error",
                message = "KNest exception",
                handlerName = ExceptionController::getKNestException.name
            )
        )

        testException(
            path = "default",
            expectedResponse = ExceptionDto(
                method = "GET",
                code = "unknown-error",
                message = "Default exception",
                handlerName = ExceptionController::getDefaultException.name
            )
        )
    }
}