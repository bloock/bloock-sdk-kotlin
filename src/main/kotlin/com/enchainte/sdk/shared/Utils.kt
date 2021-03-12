package com.enchainte.sdk.shared

import com.enchainte.sdk.message.entity.Message
import kotlinx.coroutines.delay
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.binary.StringUtils

internal class Utils {
    companion object {
        fun stringToBytes(string: String): ByteArray {
            return StringUtils.getBytesUtf8(string)
        }

        fun hexToBytes(hex: String): ByteArray {
            return Hex.decodeHex(hex)
        }

        fun bytesToString(array: ByteArray): String {
            return Hex.encodeHexString(array)
        }

        fun bytesToHex(array: ByteArray): String {
            return Hex.encodeHexString(array)
        }

        fun isHex(h: String): Boolean {
            return h.matches("^[0-9A-Fa-f]+\$".toRegex())
        }

        suspend fun sleep(ms: Int) {
            delay(ms.toLong())
        }

        fun merge(left: ByteArray, right: ByteArray): ByteArray {
            val concat = left.plus(right)
            return Message.fromUint8Array(concat).getByteArrayHash()
        }
    }
}