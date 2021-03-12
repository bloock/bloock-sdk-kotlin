package com.enchainte.sdk.shared

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalUnsignedTypes
class UtilsTest {
    @Test
    fun testIsHexSuccess() {
        val hex = "123456789abcdef"
        assertTrue(Utils.isHex(hex), "Hex string should be valid")
    }

    @Test
    fun testIsHexError() {
        val hex = "abcdefg"
        assertFalse(Utils.isHex(hex), "Hex string should not be valid")
    }

    @Test
    fun testSleep() = runBlocking {
        // TODO:
    }
}
