package com.bloock.sdk.proof.entity.dto

internal data class ProofRetrieveResponse(
    val leaves: List<String>?,
    val nodes: List<String>?,
    val depth: String?,
    val bitmap: String?,
    val root: String?,
    val networks: List<String>?
)