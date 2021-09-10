package com.bloock.sdk.infrastructure.http

import com.bloock.sdk.infrastructure.HttpClient
import com.bloock.sdk.infrastructure.http.dto.*
import com.bloock.sdk.infrastructure.http.exception.HttpRequestException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.lang.reflect.Type
import java.nio.charset.Charset
import io.ktor.client.HttpClient as KtorHttpClient


internal class HttpClientImpl(private val client: KtorHttpClient, private val httpClientData: HttpClientData): HttpClient {

    override fun setApiKey(apiKey: String) {
        httpClientData.apiKey = apiKey
    }

    override suspend fun <T: Any> get(url: String, headers: HashMap<String, String>, type: Type): T {
        return this.request(HttpMethod.Get, url, null, headers, type)
    }

    override suspend fun <T: Any> post(url: String, _body: Any?, headers: HashMap<String, String>, type: Type): T {
        return this.request(HttpMethod.Post, url, _body, headers, type)
    }

    private suspend fun <T: Any> request(_method: HttpMethod, url: String, _body: Any?, headers: HashMap<String, String>, type: Type): T {
            try {
            val response = client.request<String> {
                method = _method
                url(url)
                contentType(ContentType.Application.Json)

                if (_body != null) {
                    body = _body
                }

                if (!headers.containsKey("X-Api-Key")) {
                    header("X-Api-Key", getAuthenticationHeader())
                }

                for (header in headers) {
                    header(header.key, header.value)
                }
            }
            return Gson().fromJson(response, type)
        } catch (t: ResponseException) {
            val errorText = t.response.readText(Charset.defaultCharset())
            val error: ApiError = Gson().fromJson(errorText, object : TypeToken<ApiError>() {}.type)
            throw HttpRequestException(error.message)
        } catch (t: Throwable) {
            throw HttpRequestException(t.message)
        }
    }

    private fun getAuthenticationHeader(): String {
        return "${httpClientData.apiKey}"
    }
}
