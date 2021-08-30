//[docs](../../index.md)/[com.bloock.sdk](../index.md)/[BloockClient](index.md)/[getProof](get-proof.md)



# getProof  
[jvm]  
Content  
fun [getProof](get-proof.md)(records: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Record](../../com.bloock.sdk.record.entity/-record/index.md)>): Maybe<[Proof](../../com.bloock.sdk.proof.entity/-proof/index.md)>  
More info  


Retrieves an integrity [Proof](../../com.bloock.sdk.proof.entity/-proof/index.md) for the specified list of [Record](../../com.bloock.sdk.record.entity/-record/index.md)



#### Return  


a Maybe that will return a [Proof](../../com.bloock.sdk.proof.entity/-proof/index.md)



## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/getProof/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>records| <a name="com.bloock.sdk/BloockClient/getProof/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a><br><br>to validate<br><br>
  


#### Throws  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/getProof/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>InvalidRecordException| <a name="com.bloock.sdk/BloockClient/getProof/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a><br><br>At least one of the records sent was not well formed.<br><br>
| <a name="com.bloock.sdk/BloockClient/getProof/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>HttpRequestException| <a name="com.bloock.sdk/BloockClient/getProof/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a><br><br>Error returned by Bloock's API.<br><br>
  



