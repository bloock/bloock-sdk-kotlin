package com.enchainte.sdk.message.repository

import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.post
import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.dto.MessageRetrieveRequest
import com.enchainte.sdk.message.entity.dto.MessageRetrieveResponse
import com.enchainte.sdk.message.entity.dto.MessageWriteRequest
import com.enchainte.sdk.message.entity.dto.MessageWriteResponse

internal class MessageRepositoryImpl internal constructor(val httpClient: HttpClient, val config: ConfigService) :
    MessageRepository {
    override suspend fun sendMessages(messages: List<Message>): MessageWriteResponse? {
        val url =
            "${config.getConfiguration().HOST}${config.getConfiguration().API_VERSION}${config.getConfiguration().WRITE_ENDPOINT}"
        return httpClient.post(url, MessageWriteRequest(messages = messages.map { message -> message.getHash() }))
    }

    override suspend fun fetchMessages(messages: List<Message>): List<MessageRetrieveResponse>? {
        val url =
            "${config.getConfiguration().HOST}${config.getConfiguration().API_VERSION}${config.getConfiguration().FETCH_ENDPOINT}"

        return httpClient.post(url, MessageRetrieveRequest(messages = messages.map { it.getHash() }))


    }
}