package com.enchainte.sdk.shared.application

interface BlockchainClient {
    fun validateRoot(root: String): Boolean
}