package com.bloock.sdk.record.entity.document

import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.shared.Signature
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*

const val PROOF_KEY = "proof"
const val SIGNATURES_KEY = "signatures"

abstract class Document<T>(src: T, args: DocumentLoadArgs? = null) {

    private var data: T? = null
    private var payload: T? = null
    private var signatures: MutableList<Signature>? = null
    private var proof: Proof? = null

    init {
                setup(src)
                proof = fetchProof()
                signatures = fetchSignatures()
                data = fetchData()
                payload = fetchPayload()
    }

    abstract fun fetchData(): T?

    private fun fetchSignatures(): MutableList<Signature>? {
        val signatures = fetchMetadata(SIGNATURES_KEY)
        if (signatures != null) {
            val signatureList = object : TypeToken<ArrayList<Signature?>?>() {}.type
            try {
                return Gson().fromJson(signatures, signatureList)
            } catch(_: Exception) {}
        }
        return null
    }

    private fun fetchProof(): Proof? {
        val proof = fetchMetadata(PROOF_KEY)
        if (proof != null) {
            try {
                return Gson().fromJson(proof, Proof::class.java)
            } catch(_: Exception) {}
        }
        return null
    }

    protected abstract fun setup(src: T)
    protected abstract fun fetchMetadata(key: String): String?

    private fun fetchPayload(): T {
        val metadata = mutableMapOf<String, Any>()
        this.signatures?.let {
            metadata.put(SIGNATURES_KEY, this.signatures!!)
        }

        return this.buildFile(metadata)
    }


    fun build(): T {
        val metadata = mutableMapOf<String, Any>()
        this.proof?.let {
            metadata.put(PROOF_KEY, it)
        }
        this.signatures?.let {
            metadata.put(SIGNATURES_KEY, it)
        }

        return this.buildFile(metadata)
    }

    abstract fun buildFile(metadata: Map<String, *>): T

    fun getData(): T? = this.data
    fun getPayload(): T? = this.payload

    abstract fun getDataBytes(): ByteArray
    abstract fun getPayloadBytes(): ByteArray
    fun getSignatures() = this.signatures
    fun addSignature(signatures: Signature) {
        if (this.signatures.isNullOrEmpty()) {
            this.signatures = mutableListOf()
        }
        this.signatures!!.add(signatures)

        this.payload = this.fetchPayload()
    }

    fun getProof() = this.proof
    fun setProof(proof: Proof) {
        this.proof = proof
    }
}

open class DocumentLoadArgs