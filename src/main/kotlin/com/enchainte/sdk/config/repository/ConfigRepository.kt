package com.enchainte.sdk.config.repository

import com.enchainte.sdk.config.entity.ConfigEnvironment
import com.enchainte.sdk.config.entity.Configuration

internal interface ConfigRepository {
    suspend fun fetchConfiguration(environment: ConfigEnvironment): Configuration
    fun getConfiguration(): Configuration
}