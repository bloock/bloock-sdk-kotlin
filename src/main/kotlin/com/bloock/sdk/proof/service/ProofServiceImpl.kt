package com.bloock.sdk.proof.service

import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.record.entity.exception.InvalidRecordException
import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.proof.repository.ProofRepository
import com.bloock.sdk.shared.entity.exception.InvalidArgumentException

internal class ProofServiceImpl(private val proofRepository: ProofRepository) : ProofService {
    override suspend fun retrieveProof(records: List<Record>): Proof {
        if (records.isEmpty()) {
            throw InvalidArgumentException()
        }

        if (records.any { !Record.isValid(it) }) {
            throw InvalidRecordException()
        }

        val sorted = Record.sort(records)
        return proofRepository.retrieveProof(sorted)
    }

    override suspend fun verifyRecords(records: List<Record>): Int {
        val proof = retrieveProof(records)
        return verifyProof(proof)
    }

    override fun verifyProof(proof: Proof): Int {
        val root = proofRepository.verifyProof(proof)
        return proofRepository.validateRoot(root)
    }
}