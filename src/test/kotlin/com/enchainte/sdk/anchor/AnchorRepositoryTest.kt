package com.enchainte.sdk.anchor

import com.enchainte.sdk.anchor.entity.dto.AnchorRetrieveResponse
import com.enchainte.sdk.anchor.repository.AnchorRepository
import com.enchainte.sdk.anchor.repository.AnchorRepositoryImpl
import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.get
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AnchorRepositoryTest {
    @Test
    fun test_get_anchor_okay() {
        val configService = mockk<ConfigService>()
        every { configService.getApiBaseUrl() } returns "i'm definitely a URL"

        val httpClient = mockk<HttpClient>()
        coEvery { httpClient.get<AnchorRetrieveResponse>(any(), any()) } returns AnchorRetrieveResponse(
            id = 1,
            blockRoots = listOf("block_root"),
            networks = emptyList(),
            root = "root",
            status = "Success"
        )

        val anchorRepository: AnchorRepository = AnchorRepositoryImpl(httpClient, configService)

        runBlocking {
            val anchor = anchorRepository.getAnchor(1)

            assertEquals(anchor.id, 1)
            assertEquals(anchor.blockRoots, listOf("block_root"))
            assertEquals(anchor.networks, emptyList())
            assertEquals(anchor.root, "root")
            assertEquals(anchor.status, "Success")
        }
    }
}