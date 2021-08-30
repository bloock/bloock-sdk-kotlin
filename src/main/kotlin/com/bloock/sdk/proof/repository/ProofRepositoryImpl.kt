package com.bloock.sdk.proof.repository

import com.bloock.sdk.config.service.ConfigService
import com.bloock.sdk.infrastructure.BlockchainClient
import com.bloock.sdk.infrastructure.HttpClient
import com.bloock.sdk.infrastructure.post
import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.proof.entity.dto.ProofRetrieveRequest
import com.bloock.sdk.proof.entity.dto.ProofRetrieveResponse
import com.bloock.sdk.proof.entity.exception.InvalidProofException
import com.bloock.sdk.shared.Utils
import java.util.*

internal class ProofRepositoryImpl(
    private val httpClient: HttpClient,
    private val blockchainClient: BlockchainClient,
    private val configService: ConfigService
) : ProofRepository {

    override suspend fun retrieveProof(records: List<Record>): Proof {
        val url = "${this.configService.getApiBaseUrl()}/core/proof";
        val requestBody = ProofRetrieveRequest(records.map { it.getHash() })
        val response: ProofRetrieveResponse = httpClient.post(url, requestBody)

        return Proof(
            leaves = response.leaves ?: emptyList(),
            nodes = response.nodes ?: emptyList(),
            depth = response.depth ?: "",
            bitmap = response.bitmap ?: "",
        )
    }

    override fun verifyProof(proof: Proof): Record {
        try {
            val leaves = proof.leaves.map { Record.fromHash(it).getByteArrayHash() }.toTypedArray()
            val hashes = proof.nodes.map { Utils.hexToBytes(it) }.toTypedArray()
            val depth = Utils.hexToUint16(proof.depth)
            val bitmap = Utils.hexToBytes(proof.bitmap)

            var itLeaves = 0
            var itHashes = 0
            val stack = Stack<Pair<ByteArray, Int>>()

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
                        throw InvalidProofException()
                    }
                }

                stack.push(Pair(actHash, actDepth))
            }
            return Record.fromHash(Utils.bytesToHex(stack[0].first))
        } catch (e: Throwable) {
            throw InvalidProofException()
        }
    }

    override fun validateRoot(root: Record): Int {
        return blockchainClient.validateRoot(root.getHash())
    }
}