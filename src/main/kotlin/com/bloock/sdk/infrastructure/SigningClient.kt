package com.bloock.sdk.infrastructure

import com.bloock.sdk.shared.Signature

interface SigningClient {
    fun sign(payload: ByteArray, rawPrivateKey: String, headers :Map<String,String>): Signature
    fun verify(payload: ByteArray?, signatures: List<Signature>): Boolean
}