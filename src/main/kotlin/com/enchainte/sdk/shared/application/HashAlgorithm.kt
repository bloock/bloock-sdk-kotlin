package com.enchainte.sdk.shared.application

interface HashAlgorithm {
    fun hash(message: ByteArray): String
}