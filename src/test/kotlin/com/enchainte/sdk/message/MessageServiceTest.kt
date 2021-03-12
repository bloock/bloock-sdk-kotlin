package com.enchainte.sdk.message

import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.dto.MessageRetrieveResponse
import com.enchainte.sdk.message.entity.dto.MessageWriteResponse
import com.enchainte.sdk.message.entity.exception.InvalidMessageException
import com.enchainte.sdk.message.repository.MessageRepository
import com.enchainte.sdk.message.service.MessageService
import com.enchainte.sdk.shared.ConfigModule
import com.enchainte.sdk.shared.InfrastructureModule
import com.enchainte.sdk.shared.MessageModule
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import kotlin.test.assertEquals

class MessageServiceTest: KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            InfrastructureModule,
            ConfigModule,
            MessageModule
        )
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `send message valid`() {
        val messages: List<Message> = listOf(
            Message.fromString("Example Data 1"),
            Message.fromString("Example Data 2"),
            Message.fromString("Example Data 3")
        )
        val messageRepository = declareMock<MessageRepository> {
            runBlocking {
                given(sendMessages(messages)).will {
                    MessageWriteResponse(1, "client", messages.map { it.getHash() }, "Pending")
                }
            }
        }

        val messageService: MessageService = get()

        runBlocking {
            val result = messageService.sendMessages(messages)
            Mockito.verify(messageRepository, times(1)).sendMessages(messages)
            assertEquals(3, result.size)
        }
    }

    @Test
    fun `send message with empty list`() {
        val messages: List<Message> = emptyList()
        val messageRepository = declareMock<MessageRepository> {}

        val messageService: MessageService = get()

        runBlocking {
            val result = messageService.sendMessages(messages)
            Mockito.verify(messageRepository, times(0)).sendMessages(messages)
            assertEquals(0, result.size)
        }
    }

    @Test
    fun `send message with invalid message`() {
        val messages: List<Message> = listOf(
            Message.fromString("Example Data 2"),
            Message.fromHash("some_invalid_hash")
        )
        val messageRepository = declareMock<MessageRepository> {}

        val messageService: MessageService = get()

        runBlocking {
            try {
                messageService.sendMessages(messages)
                assert(false)
            } catch (e: InvalidMessageException) {
                assert(true)
                Mockito.verify(messageRepository, times(0)).sendMessages(messages)
            }
        }
    }

    @Test
    fun `get message valid`() {
        val messages: List<Message> = listOf(
            Message.fromString("Example Data 2"),
            Message.fromHash("some_invalid_hash")
        )
        val messageRepository = declareMock<MessageRepository> {
            runBlocking {
                given(fetchMessages(messages)).will {
                    listOf(
                        MessageRetrieveResponse(1, "client", messages[0].getHash(), "Pending"),
                        MessageRetrieveResponse(1, "client", messages[1].getHash(), "Pending")
                    )
                }
            }
        }

        val messageService: MessageService = get()

        runBlocking {
            val response = messageService.getMessages(messages)

            assertEquals(2, response.size)
            assertEquals(messages[0].getHash(), response[0].message)
            assertEquals(messages[1].getHash(), response[1].message)

            Mockito.verify(messageRepository, times(1)).fetchMessages(messages)
        }
    }

    @Test
    fun `wait message two tries`() {
        val messages: List<Message> = listOf(
            Message.fromString("Example Data 2"),
            Message.fromHash("some_invalid_hash")
        )
        val messageRepository = declareMock<MessageRepository> {
            runBlocking {
                given(fetchMessages(messages))
                    .will {
                        listOf(
                            MessageRetrieveResponse(1, "client", messages[0].getHash(), "Pending"),
                            MessageRetrieveResponse(1, "client", messages[1].getHash(), "Pending")
                        )
                    }
                    .will {
                        listOf(
                            MessageRetrieveResponse(1, "client", messages[0].getHash(), "Success"),
                            MessageRetrieveResponse(1, "client", messages[1].getHash(), "Success")
                        )
                    }
            }
        }

        val messageService: MessageService = get()

        runBlocking {
            val response = messageService.waitMessages(messages)

            assertEquals(2, response.size)
            assertEquals(messages[0].getHash(), response[0].message)
            assertEquals(messages[1].getHash(), response[1].message)

            Mockito.verify(messageRepository, times(2)).fetchMessages(messages)
        }
    }
}