package com.bloock.sdk.proof.repository

import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.proof.entity.Proof

internal interface ProofRepository {
    suspend fun retrieveProof(records: List<Record>): Proof
    fun verifyProof(proof: Proof): Record
    fun validateRoot(root: Record): Int
}