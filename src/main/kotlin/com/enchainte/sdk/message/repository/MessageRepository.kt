package com.enchainte.sdk.message.repository

import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.dto.MessageRetrieveResponse
import com.enchainte.sdk.message.entity.dto.MessageWriteResponse

internal interface MessageRepository {
    suspend fun sendMessages(messages: List<Message>): MessageWriteResponse
    suspend fun fetchMessages(messages: List<Message>): List<MessageRetrieveResponse>
}