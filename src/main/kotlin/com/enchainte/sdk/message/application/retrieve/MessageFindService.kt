package com.enchainte.sdk.message.application.retrieve

import com.enchainte.sdk.config.application.ConfigService
import com.enchainte.sdk.message.application.retrieve.dto.MessageRetrieveRequest
import com.enchainte.sdk.message.application.retrieve.dto.MessageRetrieveResponse
import com.enchainte.sdk.message.domain.Message
import com.enchainte.sdk.message.domain.MessageReceipt
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class MessageFindService(val httpClient: HttpClient, val config: ConfigService) {
    suspend fun getMessages(messages: List<Message>): List<MessageReceipt> {
        return try {
            val url = "${config.getConfiguration().HOST}${config.getConfiguration().API_VERSION}${config.getConfiguration().FETCH_ENDPOINT}"

            val response = httpClient.post<List<MessageRetrieveResponse>>{
                url(url)
                contentType(ContentType.Application.Json)
                body = MessageRetrieveRequest(messages = messages.map { it.getHash() })
            }

            response.map {
                val root: String = it.root ?: ""
                val message: String = it.message ?: ""
                val txHash: String = it.txHash ?: ""
                val status: String = it.status ?: ""
                val error: Int = it.error ?: 0

                MessageReceipt(root, message, txHash, status, error)
            }
        } catch (t: Throwable) {
            throw Exception()
        }
    }
}