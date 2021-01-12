package com.enchainte.sdk.common

import com.enchainte.sdk.shared.Factory
import com.enchainte.sdk.shared.infrastructure.http.bearer
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.json.*
import io.ktor.http.*
import kotlin.test.BeforeTest

open class BaseTest {
    @BeforeTest
    fun setup() {
        loadMockHttpEngine("test_api_key")
    }

    fun loadMockHttpEngine(key: String): HttpClient {
        val httpClient = HttpClient(MockEngine) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            install(Auth) {
                bearer {
                    apiKey = key
                }
            }
            engine {
                addHandler { request ->
                    try {
                        val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))

                        respond(HttpMockResponses.getResponse(request.url.fullUrl), headers = responseHeaders)
                    } catch (t: Throwable) {
                        respondError(HttpStatusCode.BadRequest, t.message ?: "Unexpected error")
                    }
                }
            }
        }

        Factory.httpClient = httpClient
        return httpClient
    }
}

private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"