package com.enchainte.sdk.infrastructure.http

import com.enchainte.sdk.common.fullUrl
import com.enchainte.sdk.config.entity.dto.ConfigItemResponse
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.get
import com.enchainte.sdk.infrastructure.getAzure
import com.enchainte.sdk.infrastructure.post
import com.nhaarman.mockitokotlin2.given
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import io.ktor.client.HttpClient as KtorHttpClient

internal class HttpClientTest : KoinTest {

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single {
                    KtorHttpClient(MockEngine) {
                        install(JsonFeature) {
                            serializer = GsonSerializer()
                        }
                        engine {
                            addHandler { request ->
                                when (request.url.fullUrl) {
                                    "https://api.enchainte.com/" -> {
                                        val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                                        respond("{'success': true, 'data': '${request.headers["Authorization"]}'}", headers = responseHeaders)
                                    }
                                    "https://enchainte-config.azconfig.io/" -> {
                                        val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                                        respond("{'items': []}", headers = responseHeaders)
                                    }
                                    else -> error("Unhandled ${request.url.fullUrl}")
                                }
                            }
                        }
                    }
                }
                single { HttpClientData() }
                single { HttpClientImpl(get(), get()) as HttpClient }
            }
        )
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        Mockito.mock(clazz.java)
    }

    private val httpClientData by inject<HttpClientData>()
    private val httpClient by inject<HttpClient>()

    @Test
    fun testAuthGetRequest() = runBlocking {
        val apiKey = "123456789abcdef"
        httpClientData.apiKey = apiKey

        val response = httpClient.get<String>("https://api.enchainte.com/")

        assertNotNull(response)
        assertEquals(
            "Bearer $apiKey",
            response
        )
    }

    @Test
    fun testAuthPostRequest() = runBlocking {
        val apiKey = "123456789abcdef"
        httpClientData.apiKey = apiKey

        val response = httpClient.post<String>("https://api.enchainte.com/", Any())

        assertNotNull(response)
        assertEquals(
            "Bearer $apiKey",
            response
        )
    }

    @Test
    fun testGetAzureRequest() = runBlocking {
        val response = httpClient.getAzure<List<Any>>("https://enchainte-config.azconfig.io/")

        assertNotNull(response)
        assertEquals(
            emptyList(),
            response
        )
    }
}