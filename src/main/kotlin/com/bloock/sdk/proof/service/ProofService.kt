package com.bloock.sdk.proof.service

import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.proof.entity.Proof

internal interface ProofService {
    suspend fun retrieveProof(records: List<Record<*>>): Proof
    suspend fun verifyRecords(records: List<Record<*>>, network: Network?): Int
    fun validateRoot(root: Record<*>, network: Network): Int
    fun verifySignatures(records: List<Record<*>>): Boolean
    fun verifyProof(proof: Proof): Record<*>
}