package com.bloock.sdk.record.entity.document

import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.shared.Signature
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

abstract class Document<T>(src: T, args: DocumentLoadArgs) {
    var ready: Unit
    protected var data: T? = null
    protected var payload: T? = null
    protected var signatures: MutableList<Signature?>? = null
    protected var proof: Proof? = null

    init {
        runBlocking {
            ready = GlobalScope.async {
                setup(src).await()
                proof = fetchProof()
                signatures = fetchSignatures().await()
                data = fetchData().await()
                payload = fetchPayload().await()
            }.await()
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
    fun addSignature(signatures: Signature) {
        if (!this.signatures.isNullOrEmpty()) {
            this.signatures!!.add(signatures)
        }
    }

    fun getDocProf() = this.proof
    fun setDocProof(proof: Proof) {
        this.proof = proof
    }
}

open class DocumentLoadArgs()


