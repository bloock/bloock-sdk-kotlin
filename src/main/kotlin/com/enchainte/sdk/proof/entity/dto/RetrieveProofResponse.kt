package com.enchainte.sdk.proof.entity.dto

internal data class RetrieveProofResponse(
    val nodes: List<String>,
    val depth: String,
    val bitmap: String
)