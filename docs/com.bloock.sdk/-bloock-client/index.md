//[docs](../../index.md)/[com.bloock.sdk](../index.md)/[BloockClient](index.md)



# BloockClient  
 [jvm] class [BloockClient](index.md)(**apiKey**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Entry-point to the Bloock SDK



This SDK offers all the features available in the Bloock Toolset:

<ul><li>Write records</li><li>Get records proof</li><li>Validate proof</li><li>Get records details</li></ul>   


## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient///PointingToDeclaration/"></a>apiKey| <a name="com.bloock.sdk/BloockClient///PointingToDeclaration/"></a><br><br>client API Key<br><br>
| <a name="com.bloock.sdk/BloockClient///PointingToDeclaration/"></a>environment| <a name="com.bloock.sdk/BloockClient///PointingToDeclaration/"></a><br><br>(optional) defines the Bloock's environment to use. By default: production<br><br>
  


## Constructors  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/BloockClient/#kotlin.String/PointingToDeclaration/"></a>[BloockClient](-bloock-client.md)| <a name="com.bloock.sdk/BloockClient/BloockClient/#kotlin.String/PointingToDeclaration/"></a> [jvm] fun [BloockClient](-bloock-client.md)(apiKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))Constructor with API Key that enables accessing to Bloock's functionalities   <br>


## Types  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient.Companion///PointingToDeclaration/"></a>[Companion](-companion/index.md)| <a name="com.bloock.sdk/BloockClient.Companion///PointingToDeclaration/"></a>[jvm]  <br>Content  <br>object [Companion](-companion/index.md)  <br><br><br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| <a name="kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/"></a>[equals](../../com.bloock.sdk.shared.entity.exception/-invalid-argument-exception/index.md#%5Bkotlin%2FAny%2Fequals%2F%23kotlin.Any%3F%2FPointingToDeclaration%2F%5D%2FFunctions%2F-101246078)| <a name="kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>open operator fun [equals](../../com.bloock.sdk.shared.entity.exception/-invalid-argument-exception/index.md#%5Bkotlin%2FAny%2Fequals%2F%23kotlin.Any%3F%2FPointingToDeclaration%2F%5D%2FFunctions%2F-101246078)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| <a name="com.bloock.sdk/BloockClient/getAnchor/#kotlin.Int/PointingToDeclaration/"></a>[getAnchor](get-anchor.md)| <a name="com.bloock.sdk/BloockClient/getAnchor/#kotlin.Int/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [getAnchor](get-anchor.md)(anchor: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): Single<[Anchor](../../com.bloock.sdk.anchor.entity/-anchor/index.md)>  <br>More info  <br>Gets an specific anchor id details  <br><br><br>
| <a name="com.bloock.sdk/BloockClient/getProof/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>[getProof](get-proof.md)| <a name="com.bloock.sdk/BloockClient/getProof/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [getProof](get-proof.md)(records: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Record](../../com.bloock.sdk.record.entity/-record/index.md)>): Maybe<[Proof](../../com.bloock.sdk.proof.entity/-proof/index.md)>  <br>More info  <br>Retrieves an integrity [Proof](../../com.bloock.sdk.proof.entity/-proof/index.md) for the specified list of [Record](../../com.bloock.sdk.record.entity/-record/index.md)  <br><br><br>
| <a name="com.bloock.sdk/BloockClient/getRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>[getRecords](get-records.md)| <a name="com.bloock.sdk/BloockClient/getRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [getRecords](get-records.md)(records: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Record](../../com.bloock.sdk.record.entity/-record/index.md)>): Single<[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[RecordReceipt](../../com.bloock.sdk.record.entity/-record-receipt/index.md)>>  <br>More info  <br>Retrieves all [RecordReceipt](../../com.bloock.sdk.record.entity/-record-receipt/index.md)s for the specified [Anchor](../../com.bloock.sdk.anchor.entity/-anchor/index.md)s  <br><br><br>
| <a name="kotlin/Any/hashCode/#/PointingToDeclaration/"></a>[hashCode](../../com.bloock.sdk.shared.entity.exception/-invalid-argument-exception/index.md#%5Bkotlin%2FAny%2FhashCode%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-101246078)| <a name="kotlin/Any/hashCode/#/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>open fun [hashCode](../../com.bloock.sdk.shared.entity.exception/-invalid-argument-exception/index.md#%5Bkotlin%2FAny%2FhashCode%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-101246078)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| <a name="com.bloock.sdk/BloockClient/sendRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>[sendRecords](send-records.md)| <a name="com.bloock.sdk/BloockClient/sendRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [sendRecords](send-records.md)(records: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Record](../../com.bloock.sdk.record.entity/-record/index.md)>): Single<[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[RecordReceipt](../../com.bloock.sdk.record.entity/-record-receipt/index.md)>>  <br>More info  <br>Sends a list of [Record](../../com.bloock.sdk.record.entity/-record/index.md) to Bloock  <br><br><br>
| <a name="com.bloock.sdk/BloockClient/setApiHost/#kotlin.String/PointingToDeclaration/"></a>[setApiHost](set-api-host.md)| <a name="com.bloock.sdk/BloockClient/setApiHost/#kotlin.String/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [setApiHost](set-api-host.md)(host: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))  <br>More info  <br>Overrides the API host.  <br><br><br>
| <a name="com.bloock.sdk/BloockClient/setNetworkConfiguration/#com.bloock.sdk.config.entity.Network#com.bloock.sdk.config.entity.NetworkConfiguration/PointingToDeclaration/"></a>[setNetworkConfiguration](set-network-configuration.md)| <a name="com.bloock.sdk/BloockClient/setNetworkConfiguration/#com.bloock.sdk.config.entity.Network#com.bloock.sdk.config.entity.NetworkConfiguration/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [setNetworkConfiguration](set-network-configuration.md)(network: [Network](../../com.bloock.sdk.config.entity/-network/index.md), config: [NetworkConfiguration](../../com.bloock.sdk.config.entity/-network-configuration/index.md))  <br>More info  <br>Overrides a blockchain network configuration  <br><br><br>
| <a name="kotlin/Any/toString/#/PointingToDeclaration/"></a>[toString](../../com.bloock.sdk.shared.entity.exception/-invalid-argument-exception/index.md#%5Bkotlin%2FAny%2FtoString%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-101246078)| <a name="kotlin/Any/toString/#/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>open fun [toString](../../com.bloock.sdk.shared.entity.exception/-invalid-argument-exception/index.md#%5Bkotlin%2FAny%2FtoString%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-101246078)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>
| <a name="com.bloock.sdk/BloockClient/verifyProof/#com.bloock.sdk.proof.entity.Proof/PointingToDeclaration/"></a>[verifyProof](verify-proof.md)| <a name="com.bloock.sdk/BloockClient/verifyProof/#com.bloock.sdk.proof.entity.Proof/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [verifyProof](verify-proof.md)(proof: [Proof](../../com.bloock.sdk.proof.entity/-proof/index.md)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>[jvm]  <br>Content  <br>fun [verifyProof](verify-proof.md)(proof: [Proof](../../com.bloock.sdk.proof.entity/-proof/index.md), network: [Network](../../com.bloock.sdk.config.entity/-network/index.md) = Network.ETHEREUM_MAINNET): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br>More info  <br>Verifies if the specified integrity [Proof](../../com.bloock.sdk.proof.entity/-proof/index.md) is valid and checks if it's currently included in the blockchain.  <br><br><br>
| <a name="com.bloock.sdk/BloockClient/verifyRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>[verifyRecords](verify-records.md)| <a name="com.bloock.sdk/BloockClient/verifyRecords/#kotlin.collections.List[com.bloock.sdk.record.entity.Record]/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>fun [verifyRecords](verify-records.md)(records: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Record](../../com.bloock.sdk.record.entity/-record/index.md)>): Single<[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)>  <br><br><br>[jvm]  <br>Content  <br>fun [verifyRecords](verify-records.md)(records: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[Record](../../com.bloock.sdk.record.entity/-record/index.md)>, network: [Network](../../com.bloock.sdk.config.entity/-network/index.md) = Network.ETHEREUM_MAINNET): Single<[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)>  <br>More info  <br>It retrieves a proof for the specified list of [Anchor](../../com.bloock.sdk.anchor.entity/-anchor/index.md) using [getProof](get-proof.md) and verifies it using [verifyProof](verify-proof.md).  <br><br><br>
| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a>[waitAnchor](wait-anchor.md)| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)()  <br>  <br>fun [waitAnchor](wait-anchor.md)(anchor: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), timeout: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 120000): Single<[Anchor](../../com.bloock.sdk.anchor.entity/-anchor/index.md)>  <br>More info  <br>Waits until the anchor specified is confirmed in Bloock  <br><br><br>

