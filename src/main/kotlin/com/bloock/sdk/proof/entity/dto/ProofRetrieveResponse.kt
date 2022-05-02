package com.bloock.sdk.proof.entity.dto

import com.bloock.sdk.anchor.entity.Network

internal data class ProofRetrieveResponse(
    val leaves: List<String>?,
    val nodes: List<String>?,
    val depth: String?,
    val bitmap: String?,
    val root: String?,
    val networks: List<Network>?
)