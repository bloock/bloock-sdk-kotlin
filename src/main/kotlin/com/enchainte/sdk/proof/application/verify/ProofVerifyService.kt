package com.enchainte.sdk.proof.application.verify

import com.enchainte.sdk.message.domain.Message
import com.enchainte.sdk.proof.domain.Proof
import com.enchainte.sdk.shared.application.Utils
import java.util.*

internal class ProofVerifyService() {

    fun verify(proof: Proof): Message? {
        try {
            val leaves = proof.leaves.map { Message(it).getByteArrayHash() }.toTypedArray()
            val hashes = proof.nodes.map { Utils.hexToBytes(it) }.toTypedArray()
            val depth = Utils.hexToBytes(proof.depth)
            val bitmap = Utils.hexToBytes(proof.bitmap)

            var it_leaves = 0
            var it_hashes = 0
            val stack = Stack<Pair<ByteArray, Byte>>()

            while (it_hashes < hashes.size || it_leaves < leaves.size) {
                var act_depth = depth[it_hashes + it_leaves]
                var act_hash: ByteArray

                val a = bitmap[Math.floorDiv(it_hashes + it_leaves, 8)].toInt()
                val b = (1 shl (7 - ((it_hashes + it_leaves) % 8)))
                val c = a and b

                if (c > 0) {
                    act_hash = hashes[it_hashes];
                    it_hashes += 1;
                } else {
                    act_hash = leaves[it_leaves];
                    it_leaves += 1
                }

                while (stack.size > 0 && stack[stack.size - 1].second == act_depth) {
                    try {
                        val last_hash = stack.pop()
                        act_hash = this.merge(last_hash.first, act_hash);
                        act_depth = act_depth.dec();
                    } catch (e: EmptyStackException) {
                        return null
                    }
                }

                stack.push(Pair(act_hash, act_depth))
            }
            return Message.fromHash(Utils.bytesToHex(stack[0].first))
        } catch (e: Throwable) {
            return null
        }
    }

    private fun merge(left: ByteArray, right: ByteArray): ByteArray {
        val concat = left.plus(right)
        return Message.fromUint8Array(concat).getByteArrayHash()
    }

}
