package com.bloock.sdk.infrastructure

import com.bloock.sdk.config.entity.Network

internal interface BlockchainClient {
    fun validateRoot(root: String, network: Network): Int
}