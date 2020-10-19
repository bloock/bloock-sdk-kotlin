package com.enchainte.sdk.entity

class Proof (
        val leaves: Array<Message>,
        val nodes: Array<String>,
        val depth: String,
        val bitmap: String
) {
    companion object {
        // TODO:
        fun isValid(proof: Proof): Boolean {
            return true
        }
    }
}
