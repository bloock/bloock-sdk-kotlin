package com.bloock.sdk.anchor.repository

import com.bloock.sdk.anchor.entity.Anchor
import com.bloock.sdk.anchor.entity.dto.AnchorRetrieveResponse
import com.bloock.sdk.config.service.ConfigService
import com.bloock.sdk.infrastructure.HttpClient
import com.bloock.sdk.infrastructure.get

internal class AnchorRepositoryImpl(val httpClient: HttpClient, val configService: ConfigService): AnchorRepository {
    override suspend fun getAnchor(anchor: Int): Anchor {
        val url = "${this.configService.getApiBaseUrl()}/core/anchor/${anchor}"
        val response =  httpClient.get<AnchorRetrieveResponse>(url)
        return Anchor(
            response.id,
            response.blockRoots,
            response.networks,
            response.root,
            response.status
        )
    }
}