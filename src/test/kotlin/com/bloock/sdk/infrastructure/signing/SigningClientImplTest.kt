package com.bloock.sdk.infrastructure.signing

import com.bloock.sdk.infrastructure.signing.exception.InvalidPrivateKeyException
import com.bloock.sdk.infrastructure.signing.exception.UndefinedPrivateKeyException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SigningClientImplTest {

    val signingClient = SigningClientImpl()

    @Test
    fun shouldSign() {
        val payload = """{"hello":"world"}""".toByteArray()
        val privateKey = "ecb8e554bba690eff53f1bc914941d34ae7ec446e0508d14bab3388d3e5c9457"
        val headers = mapOf(Pair("some", "header"))
        var signature = signingClient.sign(payload, privateKey, headers)

        assertNotNull(signature)
        assertNotNull(signature.header)
        assertNotNull(signature.signature)

    }

    @Test
    fun shouldSign_WithNoHeaders() {
        val payload = """{"hello":"world"}""".toByteArray()
        val privateKey = "ecb8e554bba690eff53f1bc914941d34ae7ec446e0508d14bab3388d3e5c945"
        val headers = emptyMap<String, String>()
        var signature = signingClient.sign(payload, privateKey, headers)

        assertNotNull(signature)
        assertNotNull(signature.header)
        assertNotNull(signature.signature)

    }

    @Test
    fun shouldNotSign_WithInvalidPrivateKey() {
        val payload = """{"hello":"world"}""".toByteArray()
        val privateKey = "invalid-privatekey"
        val headers = mapOf(Pair("some", "header"))

        assertThrows(InvalidPrivateKeyException::class.java) {
            signingClient.sign(payload, privateKey, headers)
        }

    }

    @Test
    fun shouldNotSign_WithEmptyPrivateKey() {
        val payload = """{"hello":"world"}""".toByteArray()
        val privateKey = ""
        val headers = mapOf(Pair("some", "header"))
        assertThrows(UndefinedPrivateKeyException::class.java) {
            signingClient.sign(payload, privateKey, headers)
        }
    }

    @Test
    fun shouldVerify_ValidJWS() {
        val payload = "hello world".toByteArray()
        val privateKey = "ecb8e554bba690eff53f1bc914941d34ae7ec446e0508d14bab3388d3e5c9457"
        val headers = mapOf(Pair("optional", "optional"))
        var signature = signingClient.sign(payload, privateKey, headers)

        var verify = signingClient.verify(payload, listOf(signature))

        assertTrue(verify)
    }
}