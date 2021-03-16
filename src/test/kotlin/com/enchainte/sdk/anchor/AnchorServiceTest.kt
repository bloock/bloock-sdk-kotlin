package com.enchainte.sdk.message

import com.enchainte.sdk.anchor.entity.Anchor
import com.enchainte.sdk.anchor.entity.dto.AnchorRetrieveResponse
import com.enchainte.sdk.anchor.repository.AnchorRepository
import com.enchainte.sdk.anchor.service.AnchorService
import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.dto.MessageRetrieveResponse
import com.enchainte.sdk.message.entity.dto.MessageWriteResponse
import com.enchainte.sdk.message.entity.exception.InvalidMessageException
import com.enchainte.sdk.message.repository.MessageRepository
import com.enchainte.sdk.message.service.MessageService
import com.enchainte.sdk.shared.AnchorModule
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

class AnchorServiceTest: KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            InfrastructureModule,
            ConfigModule,
            AnchorModule
        )
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `get anchor valid`() {
        val anchor = 1
        val anchorRepository = declareMock<AnchorRepository> {
            runBlocking {
                given(getAnchor(anchor)).will {
                    AnchorRetrieveResponse (
                        anchor,
                        emptyList(),
                        emptyList(),
                        "root",
                        "Pending"
                    )
                }
            }
        }

        val anchorService: AnchorService = get()

        runBlocking {
            val response = anchorService.getAnchor(anchor)

            assertEquals(anchor, response.id)
            assertEquals("Pending", response.status)

            Mockito.verify(anchorRepository, times(1)).getAnchor(anchor)
        }
    }

    @Test
    fun `wait for anchor`() {
        val anchor = 1
        val anchorRepository = declareMock<AnchorRepository> {
            runBlocking {
                given(getAnchor(anchor)).will {
                    AnchorRetrieveResponse (
                        anchor,
                        emptyList(),
                        emptyList(),
                        "root",
                        "Pending"
                    )
                }.will {
                    AnchorRetrieveResponse (
                        anchor,
                        emptyList(),
                        emptyList(),
                        "root",
                        "Success"
                    )
                }
            }
        }

        val anchorService: AnchorService = get()

        runBlocking {
            val response = anchorService.waitAnchor(anchor)

            assertEquals(anchor, response.id)
            assertEquals("Success", response.status)

            Mockito.verify(anchorRepository, times(2)).getAnchor(anchor)
        }
    }
}