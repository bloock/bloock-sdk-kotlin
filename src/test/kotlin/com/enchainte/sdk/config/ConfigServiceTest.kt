package com.enchainte.sdk.config

import com.enchainte.sdk.service.ConfigService
import kotlin.test.*

class ConfigServiceTest {
    @Test fun testRetrieveConfig() {
        val client = ConfigService()
        val config = client.getConfiguration()
        assertNotNull(config, "config is null")
    }
}
