//[docs](../../index.md)/[com.enchainte.sdk](../index.md)/[EnchainteClient](index.md)/[waitMessageReceipts](wait-message-receipts.md)



# waitMessageReceipts  
[jvm]  
Content  
fun [waitMessageReceipts](wait-message-receipts.md)(messages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Message](../../com.enchainte.sdk.message.domain/-message/index.md)>): Single<[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[MessageReceipt](../../com.enchainte.sdk.message.domain/-message-receipt/index.md)>>  
More info  


Waits until all specified messages are confirmed in Enchainté



#### Return  


a Single that will return a list of [MessageReceipt](../../com.enchainte.sdk.message.domain/-message-receipt/index.md)



## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| <a name="com.enchainte.sdk/EnchainteClient/waitMessageReceipts/#kotlin.collections.List[com.enchainte.sdk.message.domain.Message]/PointingToDeclaration/"></a>messages| <a name="com.enchainte.sdk/EnchainteClient/waitMessageReceipts/#kotlin.collections.List[com.enchainte.sdk.message.domain.Message]/PointingToDeclaration/"></a><br><br>to wait for<br><br>
  
  



