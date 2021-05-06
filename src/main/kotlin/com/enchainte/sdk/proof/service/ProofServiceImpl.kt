package com.enchainte.sdk.proof.service

import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.message.entity.exception.InvalidMessageException
import com.enchainte.sdk.proof.entity.Proof
import com.enchainte.sdk.proof.repository.ProofRepository
import com.enchainte.sdk.shared.entity.exception.InvalidArgumentException

internal class ProofServiceImpl(private val proofRepository: ProofRepository) : ProofService {
    override suspend fun retrieveProof(messages: List<Message>): Proof {
        if (messages.isEmpty()) {
            throw InvalidArgumentException()
        }

        if (messages.any { !Message.isValid(it) }) {
            throw InvalidMessageException()
        }

        val sorted = Message.sort(messages)
        return proofRepository.retrieveProof(sorted)
    }

    override suspend fun verifyMessages(messages: List<Message>): Int {
        val proof = retrieveProof(messages)
        return verifyProof(proof)
    }

    override fun verifyProof(proof: Proof): Int {
        val root = proofRepository.verifyProof(proof)
        return proofRepository.validateRoot(root)
    }
}