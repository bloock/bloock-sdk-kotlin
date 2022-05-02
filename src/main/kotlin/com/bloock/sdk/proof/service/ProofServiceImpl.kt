package com.bloock.sdk.proof.service

import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.record.entity.exception.InvalidRecordException
import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.proof.repository.ProofRepository
import com.bloock.sdk.shared.entity.exception.InvalidArgumentException

internal class ProofServiceImpl(private val proofRepository: ProofRepository) : ProofService {
    override suspend fun retrieveProof(records: List<Record<Any>>): Proof {
        if (records.isEmpty()) {
            throw InvalidArgumentException()
        }

        if (records.any { !Record.isValid(it) }) {
            throw InvalidRecordException()
        }

        val sorted = Record.sort(records)
        return proofRepository.retrieveProof(sorted)
    }

    override suspend fun verifyRecords(records: List<Record<Any>>, network: Network): Int {
        val proof = retrieveProof(records)
        return verifyProof(proof, network)
    }

    override fun verifyProof(proof: Proof, network: Network): Int {
        val root = proofRepository.verifyProof(proof)
        return proofRepository.validateRoot(root, network)
    }
}