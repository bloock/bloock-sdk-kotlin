//[docs](../../index.md)/[com.enchainte.sdk](../index.md)/[EnchainteClient](index.md)



# EnchainteClient  
 [jvm] class [EnchainteClient](index.md)(**apiKey**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Entry-point to the Enchainté SDK



This SDK offers all the features available in the Enchainté Toolset:

<ul><li>Write messages</li><li>Get messages proof</li><li>Validate proof</li><li>Get messages details</li></ul>   


## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| <a name="com.enchainte.sdk/EnchainteClient///PointingToDeclaration/"></a>apiKey| <a name="com.enchainte.sdk/EnchainteClient///PointingToDeclaration/"></a><br><br>client API Key<br><br>
  


## Constructors  
  
|  Name|  Summary| 
|---|---|
| <a name="com.enchainte.sdk/EnchainteClient/EnchainteClient/#kotlin.String/PointingToDeclaration/"></a>[EnchainteClient](-enchainte-client.md)| <a name="com.enchainte.sdk/EnchainteClient/EnchainteClient/#kotlin.String/PointingToDeclaration/"></a> [jvm] fun [EnchainteClient](-enchainte-client.md)(apiKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))Constructor with API Key that enables accessing to Enchainté's functionalities   <br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| <a name="kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/"></a>[equals](../../com.enchainte.sdk.proof.domain/-proof/-companion/index.md#%5Bkotlin%2FAny%2Fequals%2F%23kotlin.Any%3F%2FPointingToDeclaration%2F%5D%2FFunctions%2F-995256689)| <a name="kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>open operator fun [equals](../../com.enchainte.sdk.proof.domain/-proof/-companion/index.md#%5Bkotlin%2FAny%2Fequals%2F%23kotlin.Any%3F%2FPointingToDeclaration%2F%5D%2FFunctions%2F-995256689)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| <a name="com.enchainte.sdk/EnchainteClient/getMessages/#kotlin.collections.List[com.enchainte.sdk.message.domain.Message]/PointingToDeclaration/"></a>[getMessages](get-messages.md)| <a name="com.enchainte.sdk/EnchainteClient/getMessages/#kotlin.collections.List[com.enchainte.sdk.message.domain.Message]/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [getMessages](get-messages.md)(messages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Message](../../com.enchainte.sdk.message.domain/-message/index.md)>): Single<[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[MessageReceipt](../../com.enchainte.sdk.message.domain/-message-receipt/index.md)>>  <br>More info  <br>Retrieves the [MessageReceipt](../../com.enchainte.sdk.message.domain/-message-receipt/index.md)s for the specified [Message](../../com.enchainte.sdk.message.domain/-message/index.md)s  <br><br><br>
| <a name="com.enchainte.sdk/EnchainteClient/getProof/#kotlin.collections.List[com.enchainte.sdk.message.domain.Message]/PointingToDeclaration/"></a>[getProof](get-proof.md)| <a name="com.enchainte.sdk/EnchainteClient/getProof/#kotlin.collections.List[com.enchainte.sdk.message.domain.Message]/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [getProof](get-proof.md)(messages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Message](../../com.enchainte.sdk.message.domain/-message/index.md)>): Maybe<[Proof](../../com.enchainte.sdk.proof.domain/-proof/index.md)>  <br>More info  <br>Retrieves an integrity [Proof](../../com.enchainte.sdk.proof.domain/-proof/index.md) for the specified list of [Message](../../com.enchainte.sdk.message.domain/-message/index.md)  <br><br><br>
| <a name="kotlin/Any/hashCode/#/PointingToDeclaration/"></a>[hashCode](../../com.enchainte.sdk.proof.domain/-proof/-companion/index.md#%5Bkotlin%2FAny%2FhashCode%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-995256689)| <a name="kotlin/Any/hashCode/#/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>open fun [hashCode](../../com.enchainte.sdk.proof.domain/-proof/-companion/index.md#%5Bkotlin%2FAny%2FhashCode%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-995256689)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| <a name="com.enchainte.sdk/EnchainteClient/sendMessage/#com.enchainte.sdk.message.domain.Message/PointingToDeclaration/"></a>[sendMessage](send-message.md)| <a name="com.enchainte.sdk/EnchainteClient/sendMessage/#com.enchainte.sdk.message.domain.Message/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [sendMessage](send-message.md)(message: [Message](../../com.enchainte.sdk.message.domain/-message/index.md)): Single<[Message](../../com.enchainte.sdk.message.domain/-message/index.md)>  <br>More info  <br>Sends a [Message](../../com.enchainte.sdk.message.domain/-message/index.md) to Enchainté  <br><br><br>
| <a name="com.enchainte.sdk/EnchainteClient/setTestEnvironment/#kotlin.Boolean/PointingToDeclaration/"></a>[setTestEnvironment](set-test-environment.md)| <a name="com.enchainte.sdk/EnchainteClient/setTestEnvironment/#kotlin.Boolean/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [setTestEnvironment](set-test-environment.md)(isTest: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))  <br>More info  <br>Moves the current context to the Test environment  <br><br><br>
| <a name="kotlin/Any/toString/#/PointingToDeclaration/"></a>[toString](../../com.enchainte.sdk.proof.domain/-proof/-companion/index.md#%5Bkotlin%2FAny%2FtoString%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-995256689)| <a name="kotlin/Any/toString/#/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>open fun [toString](../../com.enchainte.sdk.proof.domain/-proof/-companion/index.md#%5Bkotlin%2FAny%2FtoString%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-995256689)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>
| <a name="com.enchainte.sdk/EnchainteClient/verifyMessages/#kotlin.collections.List[com.enchainte.sdk.message.domain.Message]/PointingToDeclaration/"></a>[verifyMessages](verify-messages.md)| <a name="com.enchainte.sdk/EnchainteClient/verifyMessages/#kotlin.collections.List[com.enchainte.sdk.message.domain.Message]/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [verifyMessages](verify-messages.md)(messages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Message](../../com.enchainte.sdk.message.domain/-message/index.md)>): Single<[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)>  <br>More info  <br>It retrieves a proof for the specified list of [Message](../../com.enchainte.sdk.message.domain/-message/index.md) using [getProof](get-proof.md) and verifies it using [verifyProof](verify-proof.md).  <br><br><br>
| <a name="com.enchainte.sdk/EnchainteClient/verifyProof/#com.enchainte.sdk.proof.domain.Proof/PointingToDeclaration/"></a>[verifyProof](verify-proof.md)| <a name="com.enchainte.sdk/EnchainteClient/verifyProof/#com.enchainte.sdk.proof.domain.Proof/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [verifyProof](verify-proof.md)(proof: [Proof](../../com.enchainte.sdk.proof.domain/-proof/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br>More info  <br>Verifies if the specified integrity [Proof](../../com.enchainte.sdk.proof.domain/-proof/index.md) is valid and checks if it's currently included in the blockchain.  <br><br><br>
| <a name="com.enchainte.sdk/EnchainteClient/waitMessageReceipts/#kotlin.collections.List[com.enchainte.sdk.message.domain.Message]/PointingToDeclaration/"></a>[waitMessageReceipts](wait-message-receipts.md)| <a name="com.enchainte.sdk/EnchainteClient/waitMessageReceipts/#kotlin.collections.List[com.enchainte.sdk.message.domain.Message]/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [waitMessageReceipts](wait-message-receipts.md)(messages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Message](../../com.enchainte.sdk.message.domain/-message/index.md)>): Single<[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[MessageReceipt](../../com.enchainte.sdk.message.domain/-message-receipt/index.md)>>  <br>More info  <br>Waits until all specified messages are confirmed in Enchainté  <br><br><br>
