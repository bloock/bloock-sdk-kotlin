package com.bloock.sdk.infrastructure

internal interface BlockchainClient {
    fun validateRoot(root: String): Int
}