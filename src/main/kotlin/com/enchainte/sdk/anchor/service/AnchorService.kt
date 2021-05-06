package com.enchainte.sdk.anchor.service

import com.enchainte.sdk.anchor.entity.Anchor

internal interface AnchorService {
    suspend fun getAnchor(id: Int): Anchor
    suspend fun waitAnchor(id: Int, timeout: Int = 120000): Anchor
}