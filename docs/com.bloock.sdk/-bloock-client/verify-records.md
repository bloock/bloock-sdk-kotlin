//[docs](../../index.md)/[com.bloock.sdk](../index.md)/[BloockClient](index.md)/[verifyRecords](verify-records.md)



# verifyRecords  
[jvm]  
Content  
fun [verifyRecords](verify-records.md)(records: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Record](../../com.bloock.sdk.record.entity/-record/index.md)>, network: [Network](../../com.bloock.sdk.config.entity/-network/index.md) = Network.ETHEREUM_MAINNET): Single<[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)>  
More info  


It retrieves a proof for the specified list of [Anchor](../../com.bloock.sdk.anchor.entity/-anchor/index.md) using [getProof](get-proof.md) and verifies it using [verifyProof](verify-proof.md).



#### Return  


a Single that will return true if valid, false if not.



## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/verifyRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a>records| <a name="com.bloock.sdk/BloockClient/verifyRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a><br><br>to verify<br><br>
  


#### Throws  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/verifyRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a>InvalidRecordException| <a name="com.bloock.sdk/BloockClient/verifyRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a><br><br>At least one of the records sent was not well formed.<br><br>
| <a name="com.bloock.sdk/BloockClient/verifyRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a>HttpRequestException| <a name="com.bloock.sdk/BloockClient/verifyRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a><br><br>Error returned by Bloock's API.<br><br>
| <a name="com.bloock.sdk/BloockClient/verifyRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a>Web3Exception| <a name="com.bloock.sdk/BloockClient/verifyRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a><br><br>Error connecting to blockchain.<br><br>
  


[jvm]  
Content  
fun [verifyRecords](verify-records.md)(records: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Record](../../com.bloock.sdk.record.entity/-record/index.md)>): Single<[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)>  



