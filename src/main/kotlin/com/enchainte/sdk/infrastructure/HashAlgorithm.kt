package com.enchainte.sdk.infrastructure

internal interface HashAlgorithm {
    fun generateHash(_data: ByteArray): String
}