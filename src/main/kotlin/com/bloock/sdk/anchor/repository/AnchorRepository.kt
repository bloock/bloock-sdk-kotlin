package com.bloock.sdk.anchor.repository

import com.bloock.sdk.anchor.entity.Anchor

internal interface AnchorRepository {
    suspend fun getAnchor(anchor: Int): Anchor
}