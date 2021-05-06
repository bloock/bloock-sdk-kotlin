package com.enchainte.sdk.infrastructure.http

import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.http.dto.*
import com.enchainte.sdk.infrastructure.http.exception.HttpRequestException
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
        var data: ApiResponse<T>
        try {
            val response = client.get<ApiResponse<Any>> {
                url(url)
                contentType(ContentType.Application.Json)

                if (!headers.containsKey("Authorization")) {
                    header("Authorization", getAuthenticationHeader())
                }

                for (header in headers) {
                    header(header.key, header.value)
                }
            }

            val rawData = Gson().toJson(response.getData())
            data = ApiResponse(
                success = response.success,
                data = Gson().fromJson(rawData, type),
                error = response.error
            )
        } catch (t: ResponseException) {
            val errorText = t.response.readText(Charset.defaultCharset())
            data = Gson().fromJson(errorText, object : TypeToken<ApiResponse<T>?>() {}.getType())
        } catch (t: Throwable) {
            throw HttpRequestException(t.message)
        }

        val success = data.isSuccess()
        val result = data.getData()

        if (success && result != null) {
            return result
        }

        throw HttpRequestException(data.getError()?.message)
    }

    override suspend fun <T: Any> post(url: String, _body: Any?, headers: HashMap<String, String>, type: Type): T {
        var data: ApiResponse<T>
        try {
            val response = client.post<ApiResponse<Any>> {
                url(url)
                contentType(ContentType.Application.Json)

                if (_body != null) {
                    body = _body
                }

                if (!headers.containsKey("Authorization")) {
                    header("Authorization", getAuthenticationHeader())
                }

                for (header in headers) {
                    header(header.key, header.value)
                }
            }

            val rawData = Gson().toJson(response.getData())
            data = ApiResponse(
                success = response.success,
                data = Gson().fromJson(rawData, type),
                error = response.error
            )
        } catch (t: ResponseException) {
            val errorText = t.response.readText(Charset.defaultCharset())
            data = Gson().fromJson(errorText, object : TypeToken<ApiResponse<T>?>() {}.getType())
        } catch (t: Throwable) {
            throw HttpRequestException(t.message)
        }

        val success = data.isSuccess()
        val result = data.getData()

        if (success && result != null) {
            return result
        }

        throw HttpRequestException(data.getError()?.message)
    }

    private fun getAuthenticationHeader(): String {
        return "${httpClientData.apiKey}"
    }
}
