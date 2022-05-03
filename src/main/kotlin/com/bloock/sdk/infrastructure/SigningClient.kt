package com.bloock.sdk.infrastructure

import com.bloock.sdk.shared.Signature

interface SigningClient {
    fun verify(payload: MutableList<Signature>?, signatures: List<Signature>): Boolean
}