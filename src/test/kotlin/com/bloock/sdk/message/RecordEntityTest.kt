package com.bloock.sdk.record

import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.shared.Headers
import com.bloock.sdk.shared.Signature
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RecordEntityTest {
    @Test
    fun test_from_hash() {
        val record = Record.fromHash("test_hash")
        assertEquals(record.getHash(), "test_hash")
    }

    @Test
    fun test_from_hex() {
        val s =
            "10101010101010101010101010101010101010101010101010101010101010101111111111111111111111111111111111111111111111111111111111111111"
        val p = "e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994a5"

        assertEquals(Record.fromHex(s).getHash(), p)
    }

    @Test
    fun test_from_string() {
        val s = "testing keccak"
        assertEquals(Record.fromString(s).getHash(), "7e5e383e8e70e55cdccfccf40dfc5d4bed935613dffc806b16b4675b555be139")
    }

    @Test
    fun test_from_bytearray() {
        val array = listOf<Byte>(16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
            16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
            17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
            17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17)
        assertEquals(Record.fromByteArray(array.toByteArray()).getHash(), "e016214a5c4abb88b8b614a916b1a6f075dfcf6fbc16c1e9d6e8ebcec81994a5")
    }

    @Test
    fun test_from_pdf() {
        val bytes = File("src/test/kotlin/com/bloock/sdk/resources/dummy.pdf").readBytes()
        val record = Record.fromPDF(bytes)
        File("src/test/kotlin/com/bloock/sdk/resources/dummy-out.pdf").writeBytes(record.retrieve()!!)
        assertEquals(record.getHash(), "cd5d993c67b0fe1f46e5169cdee04072ee72b3b110580f052988b3e6f8726f10")
    }

    @Test
    fun test_from_pdf_with_metadata() {
        val bytes = File("src/test/kotlin/com/bloock/sdk/resources/dummy-with-metadata.pdf").readBytes()
        val record = Record.fromPDF(bytes)
        assertEquals(record.getHash(), "c17a41c48474209c83dad03acee7a7e7cddd2f474de0466084c44e3e72acc3f0")
    }

    @Test
    fun test_from_json() {
        val json: JsonElement = Gson().fromJson("""{"hello":"world"}""", JsonObject::class.java)
        val record = Record.fromJSON(json)
        assertEquals(record.getHash(), "586e9b1e1681ba3ebad5ff5e6f673d3e3aa129fcdb76f92083dbc386cdde4312")
    }

    @Test
    fun test_from_json_with_metadata() {
        val content = mutableMapOf(
            Pair("_data_", mapOf(Pair("hello", "world"))),
            Pair("_metadata_", mapOf<String, Any>(
                Pair("signatures", listOf(Signature("signature1", Headers())))
            ))
        )
        val json = Gson().toJson(content)
        val record = Record.fromJSON(json)
        assertEquals(record.getHash(), "040b3fc3bb0a00d5056c6c18695ef13875cda75d97d7333ef5e8272befa4ae06")
    }

    @Test
    fun test_is_valid_okay() {
        val record = Record.fromHash("1010101010101010101010101010101010101010101010101010101010101010")

        assertTrue(Record.isValid(record))
    }

    @Test
    fun test_is_valid_wrong_char() {
        val record = Record.fromHash("1010101u101010101010101u01010101010101010101010101u010101010101010")

        assertFalse(Record.isValid(record))
    }
}