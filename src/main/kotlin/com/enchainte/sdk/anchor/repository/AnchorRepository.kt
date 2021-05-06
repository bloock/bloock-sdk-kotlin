package com.enchainte.sdk.anchor.repository

import com.enchainte.sdk.anchor.entity.Anchor

internal interface AnchorRepository {
    suspend fun getAnchor(anchor: Int): Anchor
}