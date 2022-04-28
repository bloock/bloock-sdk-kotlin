package com.bloock.sdk.record.entity.document

import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.shared.Signature
import kotlinx.coroutines.*

abstract class Document<T>(src: T, args: DocumentLoadArgs) {

    protected var data: T? = null
    protected var payload: T? = null
    private var signatures: MutableList<Signature?>? = null
    protected var proof: Proof? = null

    init {
        runBlocking {
            withContext(Dispatchers.Default) {
                setup(src).await()
                proof = fetchProof()
                signatures = fetchSignatures().await()
                data = fetchData().await()
                payload = fetchPayload().await()
            }
        }
    }

    abstract suspend fun fetchData(): Deferred<T?>

    suspend fun fetchSignatures(): Deferred<MutableList<Signature?>?> {
        return GlobalScope.async {
            return@async fetchMetadata("signatures").await()?.let {
                it as MutableList<Signature?>?
            }
        }
    }

    suspend fun fetchProof(): Proof? {
        var proof = fetchMetadata("proof").await()
        return proof?.let {
            return proof as Proof

        }

    }

    protected suspend abstract fun setup(src: T): Deferred<Unit>
    protected suspend abstract fun fetchMetadata(key: String): Deferred<Any?>
    protected suspend fun fetchPayload(): Deferred<T> {
        var metadata = mutableMapOf<String, Any>()
        this.signatures?.let {
            metadata.put("signatures", this.signatures!!)
        }

        return this.buildFile(metadata)
    }


    suspend fun build(): Deferred<T> {
        var metadata = mutableMapOf<String, Any>()
        this.proof?.let {
            metadata.put("proof", it)
        }
        this.signatures?.let {
            metadata.put("signatures", it)
        }

        return this.buildFile(metadata)
    }

    abstract suspend fun buildFile(metadata: Map<String, *>): Deferred<T>

    abstract fun getDocPayload(): Any
    abstract fun getDocData(): Any
    fun getDocSignatures() = this.signatures
    fun addSignature(signatures: Signature): Deferred<Unit> {
        if (this.signatures.isNullOrEmpty()) {
            this.signatures = mutableListOf()
        }
        this.signatures!!.add(signatures)

        return GlobalScope.async {
            this@Document.payload = this@Document.fetchPayload().await()
        }
    }

    fun getDocProof() = this.proof
    fun setDocProof(proof: Proof) {
        this.proof = proof
    }
}

open class DocumentLoadArgs()