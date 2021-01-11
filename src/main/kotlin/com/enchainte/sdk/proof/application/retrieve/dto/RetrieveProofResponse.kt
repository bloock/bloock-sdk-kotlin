package com.enchainte.sdk.proof.application.retrieve.dto

data class RetrieveProofResponse(
    val nodes: List<String>,
    val depth: String,
    val bitmap: String
)