package com.enchainte.sdk.shared.application

import com.enchainte.sdk.common.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalUnsignedTypes
class UtilsTest: BaseTest() {
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
