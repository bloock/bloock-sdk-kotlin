package com.enchainte.sdk.message.domain

import com.enchainte.sdk.shared.Factory
import com.enchainte.sdk.shared.application.Utils

class Message (private val hash: String) : Comparable<Message> {

    companion object {
        @JvmStatic
        fun fromHash(hash: String): Message {
            return Message(hash)
        }

        @JvmStatic
        fun fromHex(hex: String): Message {
            val dataArray = Utils.hexToBytes(hex)
            return generateBlake2b(dataArray)
        }

        @JvmStatic
        fun fromString(_string: String): Message {
            val dataArray = Utils.stringToBytes(_string)
            return generateBlake2b(dataArray)
        }

        @JvmStatic
        fun fromUint8Array(_uint8Array: ByteArray): Message {
            return generateBlake2b(_uint8Array)
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

        private fun generateBlake2b(_data: ByteArray): Message {
            val algorithm = Factory.getHashAlgorithm()
            return Message(algorithm.hash(_data))
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
