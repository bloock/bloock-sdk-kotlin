package com.enchainte.sdk.message.application.wait

import com.enchainte.sdk.config.application.ConfigService
import com.enchainte.sdk.shared.Factory
import com.enchainte.sdk.message.domain.Message
import com.enchainte.sdk.message.domain.MessageReceipt
import com.enchainte.sdk.shared.application.Utils
import io.ktor.client.*

internal class MessageWaitService(val config: ConfigService) {
    suspend fun waitMessages(messages: List<Message>): List<MessageReceipt> {
        var completed: Boolean
        var attempts = 0
        var messageReceipts: List<MessageReceipt>

        do {
            val messageFindService = Factory.getMessageFindService()
            messageReceipts = messageFindService.getMessages(messages)
            completed = messageReceipts.size >= messages.size && messageReceipts.all { receipt ->
                (receipt.status == "success") || (receipt.status == "error")
            }

            if (completed) break

            Utils.sleep(
                config.getConfiguration().WAIT_MESSAGE_INTERVAL_DEFAULT +
                        attempts * config.getConfiguration().WAIT_MESSAGE_INTERVAL_FACTOR
            )
            attempts += 1
        } while (!completed)

        return messageReceipts
    }
}