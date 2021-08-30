//[docs](../../index.md)/[com.bloock.sdk](../index.md)/[BloockClient](index.md)/[sendRecords](send-records.md)



# sendRecords  
[jvm]  
Content  
fun [sendRecords](send-records.md)(records: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Record](../../com.bloock.sdk.record.entity/-record/index.md)>): Single<[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[RecordReceipt](../../com.bloock.sdk.record.entity/-record-receipt/index.md)>>  
More info  


Sends a list of [Record](../../com.bloock.sdk.record.entity/-record/index.md) to Bloock



#### Return  


RxJava Single that will return a list of [RecordReceipt](../../com.bloock.sdk.record.entity/-record-receipt/index.md)



## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/sendRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>records| <a name="com.bloock.sdk/BloockClient/sendRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a><br><br>list of [Record](../../com.bloock.sdk.record.entity/-record/index.md) to send<br><br>
  


#### Throws  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/sendRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>InvalidRecordException| <a name="com.bloock.sdk/BloockClient/sendRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a><br><br>At least one of the records sent was not well formed.<br><br>
| <a name="com.bloock.sdk/BloockClient/sendRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>HttpRequestException| <a name="com.bloock.sdk/BloockClient/sendRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a><br><br>Error returned by Bloock's API.<br><br>
  



