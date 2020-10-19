package com.enchainte.sdk

import com.enchainte.sdk.`interface`.MessageCallback
import com.enchainte.sdk.entity.Message
import kotlin.test.Test
import kotlin.test.*

class EnchainteClientTest {
    @Test fun testSendMessageMethod() {
        val client = EnchainteClient("")
        assertNotNull(client.sendMessage(Message.fromHash(""), object: MessageCallback {
            override fun onMessageSuccess() {
                TODO("Not yet implemented")
            }

            override fun onMessageError(message: String) {
                TODO("Not yet implemented")
            }
        }), "sendMessage should return 'true'")
    }
}
