package com.enchainte.sdk.anchor.repository

import com.enchainte.sdk.anchor.entity.dto.AnchorRetrieveResponse
import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.get

internal class AnchorRepositoryImpl(val httpClient: HttpClient, val config: ConfigService): AnchorRepository {
    override suspend fun getAnchor(anchor: Int): AnchorRetrieveResponse? {
        val url =
            "${config.getConfiguration().HOST}${config.getConfiguration().API_VERSION}${config.getConfiguration().FETCH_ANCHOR_ENDPOINT}/$anchor"
        return httpClient.get(url)
    }
}