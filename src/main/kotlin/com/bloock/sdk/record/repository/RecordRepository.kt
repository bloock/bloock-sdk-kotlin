package com.bloock.sdk.record.repository

import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.record.entity.dto.RecordRetrieveResponse
import com.bloock.sdk.record.entity.dto.RecordWriteResponse

internal interface RecordRepository {
    suspend fun sendRecords(records: List<Record<Any>>): RecordWriteResponse
    suspend fun fetchRecords(records: List<Record<Any>>): List<RecordRetrieveResponse>
}