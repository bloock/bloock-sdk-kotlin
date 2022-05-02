package com.bloock.sdk.record.entity.document

import com.bloock.sdk.shared.Signature
import com.bloock.sdk.shared.SignatureSerializer
import com.bloock.sdk.shared.Utils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

private const val DATA_KEY: String = "_data_"
private const val METADATA_KEY: String = "_metadata_"

class JsonDocument : Document<JsonElement> {
    private var source: JsonElement? = null
    private var args: JsonDocumentLoadArgs? = null

    constructor(src: String, args: JsonDocumentLoadArgs? = null) : super(Gson().fromJson(src, JsonElement::class.java), args)
    constructor(src: JsonElement, args: JsonDocumentLoadArgs? = null): super(src, args) {
        this.source = src
        this.args = args
    }

    override fun setup(src: JsonElement) {
        this.source = src
    }

    override fun fetchMetadata(key: String): String? {
        if (this.source != null && this.source!!.isJsonObject) {
            val s = this.source!!.asJsonObject
            if (s.has(METADATA_KEY) && s.get(METADATA_KEY).isJsonObject) {
                val metadata = s.get(METADATA_KEY).asJsonObject
                return Gson().toJson(metadata.get(key))
            }
        }
        return null
    }

    override fun fetchData(): JsonElement? {
        if (this.source != null) {
            return if (this.source!!.isJsonObject && this.source!!.asJsonObject.has(DATA_KEY)) {
                this.source!!.asJsonObject.get(DATA_KEY).deepCopy()
            } else {
                this.source!!.deepCopy()
            }
        }
        return null
    }

    override fun buildFile(metadata: Map<String, *>): JsonElement {
        val gson = GsonBuilder().registerTypeAdapter(Signature::class.java, SignatureSerializer()).create()
        return if (metadata.isNotEmpty()) {
            val output = JsonObject()
            output.add(DATA_KEY, this.getData())
            output.add(METADATA_KEY, gson.toJsonTree(metadata))
            output
        } else {
            if (this.getData() != null) {
                this.getData()!!
            } else {
                JsonObject()
            }
        }

    }

    override fun getPayloadBytes(): ByteArray {
        if (this.getPayload() != null) {
            val jsonString = Gson().toJson(this.getPayload())
            return Utils.stringToBytes(jsonString)
        }
        return ByteArray(0)
    }

    override fun getDataBytes(): ByteArray {
        if (this.getData() != null) {
            val jsonString = Gson().toJson(this.getData())
            return Utils.stringToBytes(jsonString)
        }

        return ByteArray(0)
    }
}


class JsonDocumentLoadArgs : DocumentLoadArgs()
