package com.bloock.sdk.shared

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

data class Signature(
    val signature: String,
    val header: Headers
)

class SignatureSerializer : JsonSerializer<Signature?> {
    override fun serialize(src: Signature?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (src != null) {
            val json = JsonObject()
            json.add("header", context?.serialize(src.header))
            json.add("signature", context?.serialize(src.signature))
            return json
        }

        return JsonNull.INSTANCE
    }
}

data class Headers(
    val alg: String?,
    val crv: String?,
    val kid: String?,
    val kty: String?,
    val other: Map<String, Any>?
) {
    constructor() : this(null, null,null,null, null)


}