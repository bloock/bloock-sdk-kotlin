package com.enchainte.sdk.shared.application

internal interface HashAlgorithm {
    fun hash(message: ByteArray): String
}