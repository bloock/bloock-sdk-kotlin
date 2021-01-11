package com.enchainte.sdk.e2e

import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.common.BaseTest
import com.enchainte.sdk.message.domain.Message
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertTrue

class E2ETest {
    @Test
    fun e2eTest() {
        val charPool : List<Char> = ('a'..'f') + ('0'..'9')
        val randomHex = (1..16)
            .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");

        val message = Message.fromHex(randomHex)

        val client = EnchainteClient("xfJvoW4xZONxW2tDD5Vypwu3S23LfC-GaexO2epA7UjHilfFSm4gaMeZUuvcJtXz")
        println("SENDING MESSAGE: ${message.getHash()}")
        client.sendMessage(message).subscribe()

        runBlocking {
            println("WAITING MESSAGE")
            client.waitMessageReceipts(listOf(message)).blockingSubscribe()

            println("VALIDATING MESSAGE")
            var valid = false
            while (!valid) {
                valid = client.verifyMessages(listOf(message)).blockingGet()
                delay(500)
            }

            assertTrue(valid)
        }
    }
}