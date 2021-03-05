package com.enchainte.sdk.shared.application

internal interface BlockchainClient {
    fun validateRoot(root: String): Boolean
}