package com.bloock.sdk.record.service

import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.record.entity.RecordReceipt

internal interface RecordService {
    suspend fun sendRecords(records: List<Record>): List<RecordReceipt>
    suspend fun getRecords(records: List<Record>): List<RecordReceipt>
}