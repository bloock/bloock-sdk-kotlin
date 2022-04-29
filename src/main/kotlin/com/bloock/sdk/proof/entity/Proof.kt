package com.bloock.sdk.proof.entity

import com.bloock.sdk.shared.Utils
import kotlin.math.floor

data class Proof(
    val leaves: List<String>,
    val nodes: List<String>,
    val depth: String,
    val bitmap: String
) {
    companion object {
        fun isValid(proof: Any): Boolean {
            if (proof is Proof) {
                try {
                    if (proof.leaves.any { !Utils.isHex(it) || it.length != 64 }) {
                        return false
                    }

                    if (proof.nodes.any { !Utils.isHex(it) || it.length != 64 }) {
                        return false
                    }

                    if (
                        proof.depth.length != (proof.leaves.size + proof.nodes.size) * 4 &&
                        Utils.isHex(proof.depth)
                    ) {
                        return false
                    }

                    val nElements = proof.leaves.size + proof.nodes.size
                    if (proof.depth.length != nElements * 4) {
                        return false
                    }

                    if (
                        floor(proof.bitmap.length.toDouble() / 2) < floor((nElements.toDouble() + 8 - (nElements % 8)) / 8)
                    ) {
                        return false
                    }
                    return true
                } catch (t: Throwable) {
                    return false
                }
            }
            return false
        }
    }
}
