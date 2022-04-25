package com.bloock.sdk.record.entity.document

import com.bloock.sdk.proof.entity.Proof
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

abstract class Document<T>(src: T, args: DocumentLoadArgs) {
    var ready: Deferred<Unit>
    protected var data: T? = null
    protected var payload: T? = null
    protected lateinit var signatures: Signatures
    protected var proof: Proof? = null

    init {
        runBlocking {
            ready = GlobalScope.async {
                setup(src).await()
                proof = fetchProof()
                signatures = fetchSignatures().await()
                data = fetchData().await()
                payload = fetchPayload().await()

            }
        }
    }

    abstract suspend fun fetchData(): Deferred<T?>

    suspend fun fetchSignatures(): Deferred<Signatures> {
        return GlobalScope.async {
            return@async fetchMetadata("signature").await()?.let {
                if (it != Unit) {
                    it as Map<String, Any>

                    Signatures(it["signatures"] as List<Any>)
                }
                null

            } ?: Signatures(emptyList())
        }
    }

    suspend fun fetchProof(): Proof? {
        var proof = fetchMetadata("proof").await()
        if (proof != Unit) return proof as Proof
        return null
    }

    protected suspend abstract fun setup(src: T): Deferred<Unit>
    protected suspend abstract fun fetchMetadata(key: String): Deferred<Any?>
    protected suspend fun fetchPayload(): Deferred<T> {
        var signatures = Signatures(emptyList<Signature>())
        signatures?.let {
            signatures = this.signatures
        }

        return this.buildFile(mapOf(Pair("signatures", signatures)))
    }


    suspend fun build(): Deferred<T> {
        var metadata = SignatureWithProof(null, emptyList())
        this.proof?.let {
            metadata.proof = this.proof
        }
        this.signatures?.let {
            metadata.signatures = this.signatures.value
        }

        return mapOf(Pair("signatures",metadata.signatures))?.let { this.buildFile(it) }
    }

    abstract suspend fun buildFile(metadata: Map<String, *>): Deferred<T>

    abstract fun getDocPayload(): Any
    abstract fun getDocData(): Any
    abstract fun getDocSignatures(): Signatures
    abstract fun addSignature(signatures: Signatures)
}

open class Signatures(open var value: List<Any>?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Signatures) return false

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}

data class SignatureWithProof(var proof: Proof?, var signatures: List<Any>?) : Signatures(signatures)

open class DocumentLoadArgs()

data class Signature(
    val signature: String,
    val header: Headers
)

data class Headers(
    val kty: String?,
    val crv: String?,
    val alg: String?,
    val kid: String?,
    val propName: Map<String, Any>
)

