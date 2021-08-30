package com.bloock.sdk.config.repository

import com.bloock.sdk.config.entity.Configuration

internal interface ConfigRepository {
    fun getConfiguration(): Configuration
    fun setApiHost(host: String)
}