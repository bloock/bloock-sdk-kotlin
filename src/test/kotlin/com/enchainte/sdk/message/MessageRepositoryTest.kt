package com.enchainte.sdk.message

import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.post
import com.enchainte.sdk.message.entity.dto.MessageRetrieveResponse
import com.enchainte.sdk.message.entity.dto.MessageWriteResponse
import com.enchainte.sdk.message.repository.MessageRepository
import com.enchainte.sdk.message.repository.MessageRepositoryImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MessageRepositoryTest {

    @Test
    fun test_send_messages_okay() {
        val configService = mockk<ConfigService>()
        every { configService.getApiBaseUrl() } returns "api url"

        val httpClient = mockk<HttpClient>()
        coEvery { httpClient.post<MessageWriteResponse>(any(), any()) } returns MessageWriteResponse(
            anchor = 80,
            client = "ce10c769-022b-405e-8e7c-3b52eeb2a4ea",
            messages = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"),
            status = "Pending"
        )

        val messageRepository: MessageRepository = MessageRepositoryImpl(httpClient, configService)

        runBlocking {
            val response = messageRepository.sendMessages(emptyList())

            assertEquals(response.anchor, 80)
            assertEquals(response.client, "ce10c769-022b-405e-8e7c-3b52eeb2a4ea")
            assertEquals(response.messages, listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"))
            assertEquals(response.status, "Pending")
        }
    }

    @Test
    fun test_send_messages_okay_but_empty_fields() {
        val configService = mockk<ConfigService>()
        every { configService.getApiBaseUrl() } returns "api url"

        val httpClient = mockk<HttpClient>()
        coEvery { httpClient.post<MessageWriteResponse>(any(), any()) } returns MessageWriteResponse(
            anchor = 0,
            client = "",
            messages = emptyList(),
            status = "Pending"
        )

        val messageRepository: MessageRepository = MessageRepositoryImpl(httpClient, configService)

        runBlocking {
            val response = messageRepository.sendMessages(emptyList())

            assertEquals(response.anchor, 0)
            assertEquals(response.client, "")
            assertEquals(response.messages, emptyList())
            assertEquals(response.status, "Pending")
        }
    }

    @Test
    fun test_fetch_messages_okay() {
        val configService = mockk<ConfigService>()
        every { configService.getApiBaseUrl() } returns "api url"

        val httpClient = mockk<HttpClient>()
        coEvery { httpClient.post<List<MessageRetrieveResponse>>(any(), any()) } returns listOf(
            MessageRetrieveResponse(
                anchor = 80,
                client = "ce10c769-022b-405e-8e7c-3b52eeb2a4ea",
                message = "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5",
                status = "Pending"
            )
        )

        val messageRepository: MessageRepository = MessageRepositoryImpl(httpClient, configService)

        runBlocking {
            val response = messageRepository.fetchMessages(emptyList())

            assertEquals(response[0].anchor, 80)
            assertEquals(response[0].client, "ce10c769-022b-405e-8e7c-3b52eeb2a4ea")
            assertEquals(response[0].message, "02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5")
            assertEquals(response[0].status, "Pending")
        }
    }

    @Test
    fun test_fetch_messages_okay_but_empty_fields() {
        val configService = mockk<ConfigService>()
        every { configService.getApiBaseUrl() } returns "api url"

        val httpClient = mockk<HttpClient>()
        coEvery { httpClient.post<List<MessageRetrieveResponse>>(any(), any()) } returns listOf(
            MessageRetrieveResponse(
                anchor = 0,
                client = "",
                message = "",
                status = "Pending"
            )
        )

        val messageRepository: MessageRepository = MessageRepositoryImpl(httpClient, configService)

        runBlocking {
            val response = messageRepository.fetchMessages(emptyList())

            assertEquals(response[0].anchor, 0)
            assertEquals(response[0].client, "")
            assertEquals(response[0].message, "")
            assertEquals(response[0].status, "Pending")
        }
    }
}
