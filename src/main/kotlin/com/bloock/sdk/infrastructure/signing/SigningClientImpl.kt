package com.bloock.sdk.infrastructure.signing

import com.bloock.sdk.infrastructure.SigningClient
import com.bloock.sdk.shared.Signature

class SigningClientImpl: SigningClient{
    override fun verify(payload: MutableList<Signature>?, signatures: List<Signature>): Boolean {
        return true
    }
}