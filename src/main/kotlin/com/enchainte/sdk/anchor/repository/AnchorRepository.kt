package com.enchainte.sdk.anchor.repository

import com.enchainte.sdk.anchor.entity.dto.AnchorRetrieveResponse

internal interface AnchorRepository {
    suspend fun getAnchor(anchor: Int): AnchorRetrieveResponse?
}