package com.enchainte.sdk.proof.entity

class Proof(
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
