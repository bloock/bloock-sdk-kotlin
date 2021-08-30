package com.bloock.sdk.proof.service

import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.proof.entity.Proof

internal interface ProofService {
    suspend fun retrieveProof(records: List<Record>): Proof
    suspend fun verifyRecords(records: List<Record>): Int
    fun verifyProof(proof: Proof): Int
}