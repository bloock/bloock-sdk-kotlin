package com.enchainte.sdk.proof.service

import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.proof.entity.Proof

internal interface ProofService {
    suspend fun retrieveProof(messages: List<Message>): Proof
    suspend fun verifyMessages(messages: List<Message>): Int
    fun verifyProof(proof: Proof): Int
}