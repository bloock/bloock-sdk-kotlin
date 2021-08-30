package com.bloock.sdk.anchor.service

import com.bloock.sdk.anchor.entity.Anchor

internal interface AnchorService {
    suspend fun getAnchor(id: Int): Anchor
    suspend fun waitAnchor(id: Int, timeout: Int = 120000): Anchor
}