package com.enchainte.sdk.anchor.service

import com.enchainte.sdk.anchor.entity.Anchor

internal interface AnchorService {
    suspend fun getAnchor(anchor_id: Int): Anchor
    suspend fun waitAnchor(anchor_id: Int): Anchor
}