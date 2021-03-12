package com.enchainte.sdk.proof.service

import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.proof.entity.Proof
import com.enchainte.sdk.proof.repository.ProofRepository

internal class ProofServiceImpl(private val proofRepository: ProofRepository) : ProofService {
    override suspend fun retrieveProof(messages: List<Message>): Proof? {
        val sorted = Message.sort(messages)

        return proofRepository.retrieveProof(sorted)
    }

    override suspend fun verifyMessages(messages: List<Message>): Boolean {
        val proof = retrieveProof(messages) ?: return false
        return verifyProof(proof)
    }

    override fun verifyProof(proof: Proof): Boolean {
        if (!Proof.isValid(proof)) {
            return false
        }

        return try {
            val root = proofRepository.verifyProof(proof) ?: return false
            proofRepository.validateRoot(root)
        } catch (err: Throwable) {
            false
        }
    }
}