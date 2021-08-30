package com.bloock.sdk.shared

import com.bloock.sdk.record.entity.Record
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

        fun uint16ToHex(array: List<Int>): String {
            val result = ByteArray(array.size * 2)

            for (i in array.indices) {
                result[i * 2 + 1] = array[i].toByte()
                result[i * 2] = (array[i] shr 8).toByte()
            }

            return bytesToHex(result)
        }

        fun hexToUint16(hex: String): List<Int> {
            if (hex.length % 4 != 0) {
                throw Throwable("Parameter is missing last characters to be represented in uint16.")
            }
            val bytes = hexToBytes(hex)
            return List(bytes.size / 2) {
                bytes[it * 2 + 1] + (bytes[it * 2].toInt() shl 8)
            }
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
            return Record.fromByteArray(concat).getByteArrayHash()
        }
    }
}