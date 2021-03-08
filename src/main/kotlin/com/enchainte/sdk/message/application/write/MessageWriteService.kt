package com.enchainte.sdk.message.application.write

import com.enchainte.sdk.config.application.ConfigService
import com.enchainte.sdk.message.application.write.dto.MessageWriteRequest
import com.enchainte.sdk.message.application.write.dto.MessageWriteResponse
import com.enchainte.sdk.message.domain.Message
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class MessageWriteService (val httpClient: HttpClient, val config: ConfigService) {

    private var messages: HashMap<Message, SingleEmitter<Message?>> = HashMap()

    init {
        GlobalScope.launch {
            while(true) {
                delay(config.getConfiguration().WRITE_INTERVAL.toLong())
                sendMessages()
            }
        }
    }

    fun writeMessages(message: Message): Single<Message> {
        return Single.create { emitter ->
            messages[message] = emitter
        }
    }

    private suspend fun sendMessages() {
        if (messages.size == 0) {
            return
        }

        val toSend = HashMap(messages)
        messages = HashMap()

        try {
            val url = "${config.getConfiguration().HOST}${config.getConfiguration().API_VERSION}${config.getConfiguration().WRITE_ENDPOINT}"
            httpClient.post<MessageWriteResponse> {
                url(url)
                contentType(ContentType.Application.Json)
                body = MessageWriteRequest(messages = toSend.map { (key, _) -> key.getHash() })
            }

            toSend.forEach { (message, emitter) ->
                emitter.onSuccess(message)
            }
        } catch (t: Throwable) {
            toSend.forEach { (_, emitter) ->
                emitter.onError(t)
            }
        }
    }
}