package com.enchainte.sdk.anchor

import com.enchainte.sdk.anchor.entity.Anchor
import com.enchainte.sdk.anchor.entity.exception.WaitAnchorTimeoutException
import com.enchainte.sdk.anchor.repository.AnchorRepository
import com.enchainte.sdk.anchor.service.AnchorService
import com.enchainte.sdk.anchor.service.AnchorServiceImpl
import com.enchainte.sdk.config.entity.Configuration
import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.http.exception.HttpRequestException
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AnchorServiceTest {
    var counter = 0
    var maxCount = 0

    fun getAnchorSideEffect(): Anchor {
        if (counter < maxCount) {
            counter += 1
            throw HttpRequestException("anchor not ready yet")
        }

        return Anchor(1, listOf("block_root"), emptyList(), "root", "Success")
    }

    @Test
    fun test_get_anchor_okay() {
        val configService = mockk<ConfigService>()

        val anchorRepository = mockk<AnchorRepository>()
        coEvery { anchorRepository.getAnchor(1) } returns
                Anchor(1, listOf("block_root"), emptyList(), "root", "Success")


        val anchorService: AnchorService = AnchorServiceImpl(anchorRepository, configService)

        runBlocking {
            val anchor = anchorService.getAnchor(1)

            assertEquals(anchor.id, 1)
            assertEquals(anchor.blockRoots, listOf("block_root"))
            assertEquals(anchor.networks, emptyList())
            assertEquals(anchor.root, "root")
            assertEquals(anchor.status, "Success")
        }
    }

    @Test
    fun test_wait_anchor_okay_first_try() {
        counter = 0
        maxCount = 0

        val configuration = Configuration()
        configuration.WAIT_MESSAGE_INTERVAL_DEFAULT = 1
        configuration.WAIT_MESSAGE_INTERVAL_FACTOR = 0

        val configService = mockk<ConfigService>()
        every { configService.getConfiguration() } returns configuration

        val anchorRepository = mockk<AnchorRepository>()
        coEvery { anchorRepository.getAnchor(1) } returns
                getAnchorSideEffect()

        val anchorService: AnchorService = AnchorServiceImpl(anchorRepository, configService)

        runBlocking {
            val anchor = anchorService.waitAnchor(1, 5000)

            assertEquals(anchor.id, 1)
            assertEquals(anchor.blockRoots, listOf("block_root"))
            assertEquals(anchor.networks, emptyList())
            assertEquals(anchor.root, "root")
            assertEquals(anchor.status, "Success")

            coVerify(exactly = maxCount + 1) { anchorRepository.getAnchor(1) }
        }
    }

    @Test
    fun test_wait_anchor_okay_after_3_retries() {
        counter = 0
        maxCount = 3

        val configuration = Configuration()
        configuration.WAIT_MESSAGE_INTERVAL_DEFAULT = 1
        configuration.WAIT_MESSAGE_INTERVAL_FACTOR = 0

        val configService = mockk<ConfigService>()
        every { configService.getConfiguration() } returns configuration

        val anchorRepository = mockk<AnchorRepository>()
        coEvery { anchorRepository.getAnchor(1) } answers {
            getAnchorSideEffect()
        }

        val anchorService: AnchorService = AnchorServiceImpl(anchorRepository, configService)

        runBlocking {
            val anchor = anchorService.waitAnchor(1, 5000)

            assertEquals(anchor.id, 1)
            assertEquals(anchor.blockRoots, listOf("block_root"))
            assertEquals(anchor.networks, emptyList())
            assertEquals(anchor.root, "root")
            assertEquals(anchor.status, "Success")

            coVerify(exactly = maxCount + 1) { anchorRepository.getAnchor(1) }
        }
    }

    @Test
    fun test_wait_anchor_error_timeout() {
        counter = 0
        maxCount = 3

        val configuration = Configuration()
        configuration.WAIT_MESSAGE_INTERVAL_DEFAULT = 1
        configuration.WAIT_MESSAGE_INTERVAL_FACTOR = 0

        val configService = mockk<ConfigService>()
        every { configService.getConfiguration() } returns configuration

        val anchorRepository = mockk<AnchorRepository>()
        coEvery { anchorRepository.getAnchor(1) } answers {
            getAnchorSideEffect()
        }

        val anchorService: AnchorService = AnchorServiceImpl(anchorRepository, configService)

        runBlocking {
            assertFailsWith<WaitAnchorTimeoutException> {
                anchorService.waitAnchor(1, 1)
            }
        }
    }
}