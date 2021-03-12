package com.enchainte.sdk.infrastructure

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

internal interface HttpClient {
    fun setApiKey(apiKey: String)
    suspend fun <T: Any> get(url: String, headers: HashMap<String, String> = HashMap(), type: Type): T?
    suspend fun <T: Any> post(url: String, _body: Any, headers: HashMap<String, String> = HashMap(), type: Type): T?

    suspend fun <T: Any> getAzure(url: String, headers: HashMap<String, String>, type: Type): T?
}

internal suspend inline fun <reified T: Any> HttpClient.get(url: String, headers: HashMap<String, String> = HashMap()): T? = get(url, headers, object : TypeToken<T>() {}.type)
internal suspend inline fun <reified T: Any> HttpClient.post(url: String, _body: Any, headers: HashMap<String, String> = HashMap()): T? = post(url, _body, headers, object : TypeToken<T>() {}.type)
internal suspend inline fun <reified T: Any> HttpClient.getAzure(url: String, headers: HashMap<String, String> = HashMap()): T? = getAzure(url, headers, object : TypeToken<T>() {}.type)
