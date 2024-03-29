package com.bloock.sdk.infrastructure.http

import com.bloock.sdk.common.fullUrl
import com.bloock.sdk.infrastructure.HttpClient
import com.bloock.sdk.infrastructure.get
import com.bloock.sdk.infrastructure.http.exception.HttpRequestException
import com.bloock.sdk.infrastructure.post
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.http.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import io.ktor.client.HttpClient as KtorHttpClient

internal class HttpClientTest {
    val http = KtorHttpClient(MockEngine) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        engine {
            addHandler { request ->
                when (request.url.fullUrl) {
                    "https://api.bloock.com/" -> {
                        val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                        respond("'${request.headers["X-Api-Key"]}'", headers = responseHeaders)
                    }
                    "https://api.bloock.com/error" -> {
                        val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                        respond("{'message': '${request.headers["X-Api-Key"]}'}", HttpStatusCode.BadRequest, headers = responseHeaders)
                    }
                    else -> error("Unhandled ${request.url.fullUrl}")
                }
            }
        }
    }

    @Test
    fun testAuthGetRequest() = runBlocking {
        val apiKey = "123456789abcdef"

        val httpClientData = mockk<HttpClientData>()
        every { httpClientData.apiKey } returns apiKey

        val httpClient: HttpClient = HttpClientImpl(http, httpClientData)

        val response = httpClient.get<String>("https://api.bloock.com/")

        assertNotNull(response)
        assertEquals(
            apiKey,
            response
        )
    }

    @Test
    fun testAuthGetRequestError() = runBlocking {
        val apiKey = "123456789abcdef"

        val httpClientData = mockk<HttpClientData>()
        every { httpClientData.apiKey } returns apiKey

        val httpClient: HttpClient = HttpClientImpl(http, httpClientData)

        val exception = assertFailsWith<HttpRequestException> {
            httpClient.get<String>("https://api.bloock.com/error")
        }

        assertEquals(exception.message, "HttpClient response was not successful: $apiKey.")
    }

    @Test
    fun testAuthPostRequest() = runBlocking {
        val apiKey = "123456789abcdef"

        val httpClientData = mockk<HttpClientData>()
        every { httpClientData.apiKey } returns apiKey

        val httpClient: HttpClient = HttpClientImpl(http, httpClientData)

        val response = httpClient.post<String>("https://api.bloock.com/", Any())

        assertNotNull(response)
        assertEquals(
            apiKey,
            response
        )
    }

    @Test
    fun testAuthPostRequestError() = runBlocking {
        val apiKey = "123456789abcdef"

        val httpClientData = mockk<HttpClientData>()
        every { httpClientData.apiKey } returns apiKey

        val httpClient: HttpClient = HttpClientImpl(http, httpClientData)

        val exception = assertFailsWith<HttpRequestException> {
            httpClient.post<String>("https://api.bloock.com/error", null)
        }

        assertEquals(exception.message, "HttpClient response was not successful: $apiKey.")
    }
}