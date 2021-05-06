//[docs](../../index.md)/[com.enchainte.sdk](../index.md)/[EnchainteClient](index.md)/[sendMessages](send-messages.md)



# sendMessages  
[jvm]  
Content  
fun [sendMessages](send-messages.md)(messages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Message](../../com.enchainte.sdk.message.entity/-message/index.md)>): Single<[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[MessageReceipt](../../com.enchainte.sdk.message.entity/-message-receipt/index.md)>>  
More info  


Sends a list of [Message](../../com.enchainte.sdk.message.entity/-message/index.md) to Enchainté



#### Return  


RxJava Single that will return a list of [MessageReceipt](../../com.enchainte.sdk.message.entity/-message-receipt/index.md)



## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| <a name="com.enchainte.sdk/EnchainteClient/sendMessages/#kotlin.collections.List[com.enchainte.sdk.message.entity.Message]/PointingToDeclaration/"></a>messages| <a name="com.enchainte.sdk/EnchainteClient/sendMessages/#kotlin.collections.List[com.enchainte.sdk.message.entity.Message]/PointingToDeclaration/"></a><br><br>list of [Message](../../com.enchainte.sdk.message.entity/-message/index.md) to send<br><br>
  


#### Throws  
  
|  Name|  Summary| 
|---|---|
| <a name="com.enchainte.sdk/EnchainteClient/sendMessages/#kotlin.collections.List[com.enchainte.sdk.message.entity.Message]/PointingToDeclaration/"></a>InvalidMessageException| <a name="com.enchainte.sdk/EnchainteClient/sendMessages/#kotlin.collections.List[com.enchainte.sdk.message.entity.Message]/PointingToDeclaration/"></a><br><br>At least one of the messages sent was not well formed.<br><br>
| <a name="com.enchainte.sdk/EnchainteClient/sendMessages/#kotlin.collections.List[com.enchainte.sdk.message.entity.Message]/PointingToDeclaration/"></a>HttpRequestException| <a name="com.enchainte.sdk/EnchainteClient/sendMessages/#kotlin.collections.List[com.enchainte.sdk.message.entity.Message]/PointingToDeclaration/"></a><br><br>Error return by Enchainté's API.<br><br>
  



