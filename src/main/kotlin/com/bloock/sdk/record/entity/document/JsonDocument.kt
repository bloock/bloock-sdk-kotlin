package com.bloock.sdk.record.entity.document

import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

private val DATA_KEY: String = "_data_"
private val META_DATA_KEY: String = "_metadata_"

class JsonDocument(src: JsonDocumentContent, args: JsonDocumentLoadArgs) : Document<JsonDocumentContent>(src, args) {

    private lateinit var source: JsonDocumentContent

    override suspend fun setup(src: JsonDocumentContent): Deferred<Unit> {
        return GlobalScope.async {
            setSource(src)
        }
    }

    private fun setSource(src: JsonDocumentContent) {
        this.source = src
    }

    override suspend fun fetchData(): Deferred<JsonDocumentContent?> {
        return GlobalScope.async {
            this@JsonDocument.source.let {
                it.content[DATA_KEY]?.let {
                    JsonDocumentContent(
                        Gson().fromJson(
                            it.toString(),
                            MutableMap::class.java
                        ) as MutableMap<String, Any>
                    )
                } ?: this@JsonDocument.source.copy()
            }
        }
    }

    override suspend fun fetchMetadata(key: String): Deferred<Any?> {
        return GlobalScope.async {
            this@JsonDocument.source?.let {
                var metadata = it.content[META_DATA_KEY]

                if (metadata != null) {
                    metadata as Map<String, Any>
                    return@async metadata[key]
                }
                return@async null
            }
        }
    }

    override suspend fun buildFile(metadata: Map<String, *>): Deferred<JsonDocumentContent> {
        return GlobalScope.async {
            if (metadata.isNotEmpty()) {
                var output = mutableMapOf<String, Any>()
                output.put(DATA_KEY, Gson().toJson(this@JsonDocument.data?.content))
                output.put(META_DATA_KEY, metadata)
                return@async JsonDocumentContent(output)
            } else {
                return@async this@JsonDocument.data?.content.let {
                    JsonDocumentContent(it!!)
                }
            }
        }

    }

    override fun getDocPayload() = Gson().toJson(this.payload?.content)
    override fun getDocData() = Gson().toJson(this.data?.content)
    override fun getDataBytes() = this.data?.content.toString().toByteArray()
    override fun getPayloadBytes() = this.payload?.content.toString().toByteArray()
}


class JsonDocumentLoadArgs : DocumentLoadArgs()


data class JsonDocumentContent(val content: MutableMap<String, Any>)
