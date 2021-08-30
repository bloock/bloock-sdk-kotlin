package com.bloock.sdk.record

import com.bloock.sdk.record.entity.Record
import org.junit.jupiter.api.Test
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