package com.bloock.sdk.record.entity.document

import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class JsonDocument(src: JsonDocumentContent, args: JsonDocumentLoadArgs) : Document<JsonDocumentContent>(src, args) {

    private val DATA_KEY = "_data_"
    private val META_DATA_KEY = "_metadata_"
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
            fetchDataAsync()
        }
    }

    private fun fetchDataAsync(): JsonDocumentContent {
        this.source.let {
            it.content[DATA_KEY]?.let {
                return JsonDocumentContent(Gson().fromJson(it.toString(), MutableMap::class.java) as MutableMap<String, Any>)
            } ?: return this.source.copy()
        }
    }

    override suspend fun fetchMetadata(key: String): Deferred<Any?> {
        return GlobalScope.async {
            fetchMetadataAsync(key)
        }
    }

    private fun fetchMetadataAsync(key: String): Any {
        return this.source?.let {
            var metadata = it.content[META_DATA_KEY]
            if (metadata != null) it.content[key]
        }
    }

    override suspend fun buildFile(metadata: Map<String, *>): Deferred<JsonDocumentContent> {
        return GlobalScope.async {
            buildFileAsync(metadata)
        }

    }

    private fun buildFileAsync(metadata: Map<String, *>): JsonDocumentContent {
        if (metadata.isNotEmpty()) {
            var output = this.source.copy()
            output.content.put(this.data?.content.toString(), metadata)
            return output
        } else {
            return this.data?.copy() ?: JsonDocumentContent(mutableMapOf())
        }
    }

    override fun getDocPayload() = Gson().toJson(this.payload?.content)
    override fun getDocData() = Gson().toJson(this.data?.content)
    override fun getDocSignatures() = this.signatures
    override fun addSignature(signatures: Signatures) {
        this.signatures = signatures
    }

}


class JsonDocumentLoadArgs : DocumentLoadArgs()


data class JsonDocumentContent(val content: MutableMap<String, Any>)
