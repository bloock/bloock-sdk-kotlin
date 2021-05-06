package com.enchainte.sdk.message

import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.dto.MessageRetrieveResponse
import com.enchainte.sdk.message.entity.dto.MessageWriteResponse
import com.enchainte.sdk.message.entity.exception.InvalidMessageException
import com.enchainte.sdk.message.repository.MessageRepository
import com.enchainte.sdk.message.service.MessageService
import com.enchainte.sdk.message.service.MessageServiceImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MessageServiceTest {
    @Test
    fun test_send_messages_okay() {
        val messageRepository = mockk<MessageRepository>()
        coEvery { messageRepository.sendMessages(any()) } returns MessageWriteResponse(
            anchor = 80,
            client = "ce10c769-022b-405e-8e7c-3b52eeb2a4ea",
            messages = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"),
            status = "Pending"
        )

        val messageService: MessageService = MessageServiceImpl(messageRepository)

        runBlocking {
            val result = messageService.sendMessages(listOf(
                Message.fromHash("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5")
            ))

            assertEquals(result[0].anchor, 80)
            assertEquals(result[0].client, "ce10c769-022b-405e-8e7c-3b52eeb2a4ea")
            assertEquals(result[0].message, "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5")
            assertEquals(result[0].status, "Pending")
        }
    }

    @Test
    fun test_send_messages_some_invalid_message_error() {
        val messageRepository = mockk<MessageRepository>()
        coEvery { messageRepository.sendMessages(any()) } returns MessageWriteResponse(
            anchor = 80,
            client = "ce10c769-022b-405e-8e7c-3b52eeb2a4ea",
            messages = listOf(
                "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5",
                "message2",
                ""
            ),
            status = "Pending"
        )

        val messageService: MessageService = MessageServiceImpl(messageRepository)

        runBlocking {
            assertFailsWith<InvalidMessageException> {
                messageService.sendMessages(listOf(
                    Message.fromHash("message")
                ))
            }
        }
    }

    @Test
    fun test_get_messages_okay() {
        val messageRepository = mockk<MessageRepository>()
        coEvery { messageRepository.fetchMessages(any()) } returns listOf(
            MessageRetrieveResponse(
                anchor = 80,
                client = "ce10c769-022b-405e-8e7c-3b52eeb2a4ea",
                message = "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5",
                status = "Pending"
            )
        )

        val messageService: MessageService = MessageServiceImpl(messageRepository)

        runBlocking {
            val result = messageService.getMessages(listOf(
                Message.fromString("message")
            ))

            assertEquals(result[0].anchor, 80)
            assertEquals(result[0].client, "ce10c769-022b-405e-8e7c-3b52eeb2a4ea")
            assertEquals(result[0].message, "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5")
            assertEquals(result[0].status, "Pending")
        }
    }
}