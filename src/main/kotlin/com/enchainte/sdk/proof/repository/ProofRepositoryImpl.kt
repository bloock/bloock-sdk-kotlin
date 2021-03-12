package com.enchainte.sdk.proof.repository

import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.infrastructure.BlockchainClient
import com.enchainte.sdk.infrastructure.HttpClient
import com.enchainte.sdk.infrastructure.post
import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.proof.entity.Proof
import com.enchainte.sdk.proof.entity.dto.RetrieveProofRequest
import com.enchainte.sdk.proof.entity.dto.RetrieveProofResponse
import com.enchainte.sdk.shared.Utils
import java.util.*

internal class ProofRepositoryImpl(
    private val httpClient: HttpClient,
    private val blockchainClient: BlockchainClient,
    private val config: ConfigService
) : ProofRepository {

    override suspend fun retrieveProof(messages: List<Message>): Proof? {
        return try {
            val url =
                "${config.getConfiguration().HOST}${config.getConfiguration().API_VERSION}${config.getConfiguration().PROOF_ENDPOINT}"
            val requestBody = RetrieveProofRequest(messages.map { it.getHash() })
            val response: RetrieveProofResponse = httpClient.post(url, requestBody) ?: return null

            Proof(requestBody.messages, response.nodes, response.depth, response.bitmap)
        } catch (t: Throwable) {
            null
        }
    }

    override fun verifyProof(proof: Proof): Message? {
        try {
            val leaves = proof.leaves.map { Message(it).getByteArrayHash() }.toTypedArray()
            val hashes = proof.nodes.map { Utils.hexToBytes(it) }.toTypedArray()
            val depth = Utils.hexToBytes(proof.depth)
            val bitmap = Utils.hexToBytes(proof.bitmap)

            var itLeaves = 0
            var itHashes = 0
            val stack = Stack<Pair<ByteArray, Byte>>()

            while (itHashes < hashes.size || itLeaves < leaves.size) {
                var actDepth = depth[itHashes + itLeaves]
                var actHash: ByteArray

                val a = bitmap[Math.floorDiv(itHashes + itLeaves, 8)].toInt()
                val b = (1 shl (7 - ((itHashes + itLeaves) % 8)))
                val c = a and b

                if (c > 0) {
                    actHash = hashes[itHashes]
                    itHashes += 1
                } else {
                    actHash = leaves[itLeaves]
                    itLeaves += 1
                }

                while (stack.size > 0 && stack[stack.size - 1].second == actDepth) {
                    try {
                        val lastHash = stack.pop()
                        actHash = Utils.merge(lastHash.first, actHash)
                        actDepth = actDepth.dec()
                    } catch (e: EmptyStackException) {
                        return null
                    }
                }

                stack.push(Pair(actHash, actDepth))
            }
            return Message.fromHash(Utils.bytesToHex(stack[0].first))
        } catch (e: Throwable) {
            return null
        }
    }

    override fun validateRoot(root: Message): Boolean {
        return blockchainClient.validateRoot(root.getHash())
    }
}