package com.enchainte.sdk.message.domain

import com.enchainte.sdk.common.BaseTest
import com.enchainte.sdk.message.domain.Message
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MessageTest: BaseTest() {
    @Test
    fun testIsValid() {
        val message = Message.fromHash("123456789abcdef123456789abcdef123456789abcdef123456789abcdef1234");
        assertTrue(
            Message.isValid(message)
        )
    }

    @Test
    fun testIsNotValid() {
        val message = "123456789abcdef123456789abcdef123456789abcdef123456789abcdef1234";
        assertFalse(
            Message.isValid(message)
        )
    }

    @Test
    fun testIsValidErrorLenght() {
        val message = "123456789abcdef123456789abcdef123456789abcdef123456789abcdef123456789abcdef";
        assertFalse(
            Message.isValid(message)
        )
    }

    @Test
    fun testIsValidErrorHex() {
        val message = "123456789abcdef123456789abcdef123456789abcdef123456789abcdef123g";
        assertFalse(
            Message.isValid(message)
        )
    }
}