package com.enchainte.sdk

import com.enchainte.sdk.common.BaseTest
import com.enchainte.sdk.common.ClientTest
import com.enchainte.sdk.message.domain.Message
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

@ClientTest
class EnchainteClientTest: BaseTest() {

    @Test
    fun testSendMessageMethod(client: EnchainteClient) {
        assertNotNull(client)
    }
}
