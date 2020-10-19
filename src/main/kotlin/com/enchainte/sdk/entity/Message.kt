package com.enchainte.sdk.entity

import com.enchainte.sdk.utils.Utils
import org.json.JSONObject

public class Message (private val hash: String) {

    companion object {
        // TODO:
        fun from(data: Any) : Message {
            return Message("")
        }

        fun fromHash(hash: String): Message {
            return Message(hash);
        }

        fun fromHex(hex: String): Message {
            val dataArray = Utils.hexToBytes(hex);
            return generateBlake2b(dataArray);
        }

        fun fromString(_string: String): Message {
            val dataArray = Utils.stringToBytes(_string);
            return generateBlake2b(dataArray);
        }

        fun fromUint8Array(_uint8Array: ByteArray): Message {
            return generateBlake2b(_uint8Array);
        }

        // TODO:
        fun sort(messages: Array<Message>): Array<Message> {
            return messages
        }

        // TODO:
        fun isValid(message: Any): Boolean {
            return true
        }

        // TODO:
        private fun generateBlake2b(_data: ByteArray): Message {
            return Message("")
        }
    }

    public fun getHash(): String {
        return this.hash;
    }

    public fun getUint8ArrayHash(): ByteArray {
        return Utils.hexToBytes(this.hash);
    }
}
