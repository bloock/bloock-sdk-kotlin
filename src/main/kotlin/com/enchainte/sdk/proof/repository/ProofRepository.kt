package com.enchainte.sdk.proof.repository

import com.enchainte.sdk.message.entity.Message
import com.enchainte.sdk.proof.entity.Proof

internal interface ProofRepository {
    suspend fun retrieveProof(messages: List<Message>): Proof
    fun verifyProof(proof: Proof): Message
    fun validateRoot(root: Message): Int
}