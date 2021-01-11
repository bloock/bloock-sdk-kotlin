package com.enchainte.sdk.common

import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class HttpMockResponses {
    companion object {
        fun getResponse(url: String): String {
            println("REQUEST TO: $url")
            return when (url) {
                "https://api.enchainte.com/" -> {
                    "Hello, world"
                }
                "https://enchainte-config.azconfig.io" -> {
                    "Hello, world"
                }
                else -> throw Error("Not mocked request")
            }
        }
    }
}