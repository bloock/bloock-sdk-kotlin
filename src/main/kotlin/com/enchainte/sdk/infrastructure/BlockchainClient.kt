package com.enchainte.sdk.infrastructure

internal interface BlockchainClient {
    fun validateRoot(root: String): Int
}