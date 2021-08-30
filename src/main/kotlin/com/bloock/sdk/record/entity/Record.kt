package com.bloock.sdk.record.entity

import com.bloock.sdk.infrastructure.HashAlgorithm
import com.bloock.sdk.infrastructure.hashing.Blake2b
import com.bloock.sdk.infrastructure.hashing.Keccak
import com.bloock.sdk.shared.Utils

class Record(private val hash: String) : Comparable<Record> {

    companion object {

        private val hashingAlgorithm: HashAlgorithm = Keccak()

        @JvmStatic
        fun fromHash(hash: String): Record {
            return Record(hash)
        }

        @JvmStatic
        fun fromHex(hex: String): Record {
            val dataArray = Utils.hexToBytes(hex)
            return Record(hashingAlgorithm.generateHash(dataArray))
        }

        @JvmStatic
        fun fromString(_string: String): Record {
            val dataArray = Utils.stringToBytes(_string)
            return Record(hashingAlgorithm.generateHash(dataArray))
        }

        @JvmStatic
        fun fromByteArray(_uint8Array: ByteArray): Record {
            return Record(hashingAlgorithm.generateHash(_uint8Array))
        }

        @JvmStatic
        fun sort(records: List<Record>): List<Record> {
            records.sorted()
            return records
        }

        @JvmStatic
        fun isValid(record: Any): Boolean {
            if (record is Record) {
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

    fun getByteArrayHash(): ByteArray {
        return Utils.hexToBytes(this.hash)
    }

    override fun compareTo(other: Record): Int = this.getHash().compareTo(other.getHash())
    override fun toString(): String = hash
}
