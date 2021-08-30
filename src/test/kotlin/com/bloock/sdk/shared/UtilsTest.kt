package com.bloock.sdk.shared

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalUnsignedTypes
class UtilsTest {
    @Test
    fun test_is_hex_ok() {
        val hex = "123456789abcdef"
        assertTrue(Utils.isHex(hex), "Hex string should be valid")
    }

    @Test
    fun test_is_hex_error() {
        val hex = "abcdefg"
        assertFalse(Utils.isHex(hex), "Hex string should not be valid")
    }

    @Test
    fun test_uint16_to_string() = runBlocking {
        val hex = "0100";
        val result = ByteArray(2)
        result[0] = 1
        result[1] = 0

        val arr = Utils.hexToUint16(hex);
        assertEquals(arr, listOf(256))
        val arr2 = Utils.hexToBytes(hex);
        assertTrue(arr2.toTypedArray().contentEquals(result.toTypedArray()))

        assertEquals(
            Utils.uint16ToHex(arr),
            Utils.bytesToHex(result)
        )
    }
}
