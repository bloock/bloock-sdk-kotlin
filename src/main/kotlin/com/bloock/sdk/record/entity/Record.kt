package com.bloock.sdk.record.entity

import com.bloock.sdk.infrastructure.HashAlgorithm
import com.bloock.sdk.infrastructure.hashing.Keccak
import com.bloock.sdk.infrastructure.signing.SigningClientImpl
import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.record.entity.document.Document
import com.bloock.sdk.record.entity.document.JsonDocument
import com.bloock.sdk.record.entity.document.PDFDocument
import com.bloock.sdk.record.entity.exception.NoSignatureFoundException
import com.bloock.sdk.shared.Utils
import com.google.gson.JsonElement

class Record<T>(private val hash: String, private val document: Document<T>? = null) : Comparable<Record<T>> {
    private val signing = SigningClientImpl()

    companion object {

        private val hashingAlgorithm: HashAlgorithm = Keccak()

        @JvmStatic
        fun fromHash(hash: String): Record<Any> {
            return Record(hash)
        }

        @JvmStatic
        fun fromHex(hex: String): Record<Any> {
            val dataArray = Utils.hexToBytes(hex)
            return Record(hashingAlgorithm.generateHash(dataArray))
        }

        @JvmStatic
        fun fromString(_string: String): Record<Any> {
            val dataArray = Utils.stringToBytes(_string)
            return Record(hashingAlgorithm.generateHash(dataArray))
        }

        @JvmStatic
        fun fromByteArray(_uint8Array: ByteArray): Record<Any> {
            return Record(hashingAlgorithm.generateHash(_uint8Array))
        }

        @JvmStatic
        private fun <D> fromDocument(document: Document<D>): Record<D> {
            return Record(hashingAlgorithm.generateHash(document.getPayloadBytes()), document)
        }

        @JvmStatic
        public fun fromPDF(src: ByteArray): Record<ByteArray> {
            val pdf = PDFDocument(src)
            return fromDocument(pdf)
        }

        @JvmStatic
        public fun fromJSON(src: JsonElement): Record<JsonElement> {
            val pdf = JsonDocument(src)
            return fromDocument(pdf)
        }

        @JvmStatic
        public fun fromJSON(src: String): Record<JsonElement> {
            val pdf = JsonDocument(src)
            return fromDocument(pdf)
        }

        @JvmStatic
        fun sort(records: List<Record<Any>>): List<Record<Any>> {
            records.sorted()
            return records
        }

        @JvmStatic
        fun isValid(record: Any): Boolean {
            if (record is Record<*>) {
                val hash = record.getHash()
                if (hash.length == 64 && Utils.isHex(hash)) {
                    return true
                }
            }
            return false
        }
    }

    fun getHash(): String {
        return this.hash
    }

    fun retrieve(): T? {
        return this.document?.build()
    }

    fun getByteArrayHash(): ByteArray {
        return Utils.hexToBytes(this.hash)
    }

    override fun compareTo(other: Record<T>): Int = this.getHash().compareTo(other.getHash())
    override fun toString(): String = hash
    fun verify(): Boolean {
        val signatures = this.document?.getSignatures()
        if (signatures?.isNotEmpty() == true) {
            return this.signing.verify(this.document?.getSignatures(), signatures)
        } else {
           //TODO throw NoSignatureFoundException()
            return true
        }
    }

    fun setProof(proof: Proof) = this.document?.setProof(proof) ?: Unit
    fun getProof(proof: Proof) = this.document?.getProof() ?: Unit


}
