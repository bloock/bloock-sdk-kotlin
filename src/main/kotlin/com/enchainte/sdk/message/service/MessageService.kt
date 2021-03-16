package com.enchainte.sdk.message.service

import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.MessageReceipt

internal interface MessageService {
    suspend fun sendMessages(messages: List<Message>): List<MessageReceipt>
    suspend fun getMessages(messages: List<Message>): List<MessageReceipt>
}