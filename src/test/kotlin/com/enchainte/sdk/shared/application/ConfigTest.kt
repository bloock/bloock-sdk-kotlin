package com.enchainte.sdk.shared.application

import com.enchainte.sdk.common.BaseTest
import com.enchainte.sdk.shared.Factory
import kotlin.test.Test
import kotlin.test.assertNotNull

class ConfigTest: BaseTest() {
    @Test fun testRetrieveConfig() {
        val client = Factory.getConfig()
        val config = client.getConfiguration()
        assertNotNull(config, "config is null")
    }
}
