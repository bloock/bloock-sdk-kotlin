package com.enchainte.sdk.message.service

import com.enchainte.sdk.config.repository.ConfigRepository
import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.MessageReceipt
import com.enchainte.sdk.message.entity.exception.InvalidMessageException
import com.enchainte.sdk.message.repository.MessageRepository
import com.enchainte.sdk.shared.Utils

internal class MessageServiceImpl internal constructor(
    private val messageRepository: MessageRepository
) : MessageService {

    override suspend fun sendMessages(messages: List<Message>): List<MessageReceipt> {
        if (messages.isEmpty()) {
            return emptyList()
        }

        for (message in messages) {
            if (!Message.isValid(message)) {
                throw InvalidMessageException()
            }
        }

        val response = messageRepository.sendMessages(messages)

        val result: ArrayList<MessageReceipt> = ArrayList()
        for (message in messages) {
            result.add(
                MessageReceipt(
                    response?.anchor ?: 0,
                    response?.client ?: "",
                    message.getHash(),
                    response?.status ?: ""
                )
            )
        }

        return result
    }

    override suspend fun getMessages(messages: List<Message>): List<MessageReceipt> {
        val response = messageRepository.fetchMessages(messages) ?: emptyList()

        return response.map {
            val anchor: Int = it.anchor ?: 0
            val message: String = it.message ?: ""
            val client: String = it.client ?: ""
            val status: String = it.status ?: ""

            MessageReceipt(anchor, client, message, status)
        }
    }

}