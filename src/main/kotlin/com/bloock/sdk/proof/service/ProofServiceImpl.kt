package com.bloock.sdk.proof.service

import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.record.entity.exception.InvalidRecordException
import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.proof.entity.exception.InvalidProofException
import com.bloock.sdk.proof.entity.exception.InvalidSignatureException
import com.bloock.sdk.proof.repository.ProofRepository
import com.bloock.sdk.shared.entity.exception.InvalidArgumentException

internal class ProofServiceImpl(private val proofRepository: ProofRepository) : ProofService {

    override suspend fun retrieveProof(records: List<Record<*>>): Proof {
        if (records.isEmpty()) {
            throw InvalidArgumentException()
        }

        if (records.any { !Record.isValid(it) }) {
            throw InvalidRecordException()
        }

        if (records.size == 1) {
            val proof = records[0].getProof()
            if (proof != null) {
                return proof
            }
        }

        val sorted = Record.sort(records as List<Record<Any>>)


        val proof = proofRepository.retrieveProof(sorted)

        if (sorted.size == 1) {
            sorted[0].setProof(proof)
        }

        return proof
    }

    override suspend fun verifyRecords(records: List<Record<*>>, network: Network?): Int {
        val proof = retrieveProof(records)
        val verified = this.verifySignatures(records)
        if (!verified) throw InvalidSignatureException()
        if (proof == null) throw InvalidProofException()

        val finalNetwork: Network
        if (network != null) {
            finalNetwork = network
        } else {
            finalNetwork = selectNetwork(proof.anchor?.networks ?: emptyList())
        }

        val root = this.verifyProof(proof)
        return validateRoot(root, finalNetwork)
    }

    override fun verifyProof(proof: Proof): Record<*> = proofRepository.verifyProof(proof)


    override fun validateRoot(root: Record<*>, network: Network) = this.proofRepository.validateRoot(root, network)

    override fun verifySignatures(records: List<Record<*>>): Boolean {
        if (records.isEmpty()) throw InvalidArgumentException()

        for (record in records) {
            if (!record.verify()) {
                return false
            }
        }

        return true
    }
}

private fun selectNetwork(networks: List<com.bloock.sdk.anchor.entity.Network>): Network {
    for (n in networks) {
        if (n.name == Network.ETHEREUM_MAINNET.name) {
            return Network.ETHEREUM_MAINNET
        }
    }
    return when (networks[0].name.toUpperCase()) {
        Network.BLOOCK_CHAIN.name -> Network.BLOOCK_CHAIN
        Network.ETHEREUM_RINKEBY.name -> Network.ETHEREUM_RINKEBY
        else -> Network.ETHEREUM_MAINNET
    }

}
