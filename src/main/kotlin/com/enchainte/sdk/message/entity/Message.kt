package com.enchainte.sdk.message.entity

import com.enchainte.sdk.infrastructure.HashAlgorithm
import com.enchainte.sdk.infrastructure.hashing.Blake2b
import com.enchainte.sdk.shared.Utils

class Message(private val hash: String) : Comparable<Message> {

    companion object {

        private val hashingAlgorithm: HashAlgorithm = Blake2b()

        @JvmStatic
        fun fromHash(hash: String): Message {
            return Message(hash)
        }

        @JvmStatic
        fun fromHex(hex: String): Message {
            val dataArray = Utils.hexToBytes(hex)
            return Message(hashingAlgorithm.generateHash(dataArray))
        }

        @JvmStatic
        fun fromString(_string: String): Message {
            val dataArray = Utils.stringToBytes(_string)
            return Message(hashingAlgorithm.generateHash(dataArray))
        }

        @JvmStatic
        fun fromUint8Array(_uint8Array: ByteArray): Message {
            return Message(hashingAlgorithm.generateHash(_uint8Array))
        }

        @JvmStatic
        fun sort(messages: List<Message>): List<Message> {
            messages.sorted()
            return messages
        }

        @JvmStatic
        fun isValid(message: Any): Boolean {
            if (message is Message) {
                val hash = message.getHash()
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

    override fun compareTo(other: Message): Int = this.getHash().compareTo(other.getHash())
    override fun toString(): String = hash
}
