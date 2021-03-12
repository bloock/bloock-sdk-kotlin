//[docs](../../index.md)/[com.enchainte.sdk](../index.md)/[EnchainteClient](index.md)/[verifyMessages](verify-messages.md)



# verifyMessages  
[jvm]  
Content  
fun [verifyMessages](verify-messages.md)(messages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Message](../../com.enchainte.sdk.message.entity/-message/index.md)>): Single<[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)>  
More info  


It retrieves a proof for the specified list of [Message](../../com.enchainte.sdk.message.entity/-message/index.md) using [getProof](get-proof.md) and verifies it using [verifyProof](verify-proof.md).



#### Return  


a Single that will return true if valid, false if not.



## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| <a name="com.enchainte.sdk/EnchainteClient/verifyMessages/#kotlin.collections.List[com.enchainte.sdk.message.entity.Message]/PointingToDeclaration/"></a>messages| <a name="com.enchainte.sdk/EnchainteClient/verifyMessages/#kotlin.collections.List[com.enchainte.sdk.message.entity.Message]/PointingToDeclaration/"></a><br><br>to verify<br><br>
  
  



