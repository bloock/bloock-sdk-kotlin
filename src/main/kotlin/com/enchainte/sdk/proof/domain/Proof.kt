package com.enchainte.sdk.proof.domain

class Proof (
    val leaves: List<String>,
    val nodes: List<String>,
    val depth: String,
    val bitmap: String
) {
    companion object {
        fun isValid(proof: Any): Boolean {
            return proof is Proof
        }
    }
}
