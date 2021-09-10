//[docs](../../index.md)/[com.bloock.sdk](../index.md)/[BloockClient](index.md)/[verifyProof](verify-proof.md)



# verifyProof  
[jvm]  
Content  
fun [verifyProof](verify-proof.md)(proof: [Proof](../../com.bloock.sdk.proof.entity/-proof/index.md), network: [Network](../../com.bloock.sdk.config.entity/-network/index.md) = Network.ETHEREUM_MAINNET): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  
More info  


Verifies if the specified integrity [Proof](../../com.bloock.sdk.proof.entity/-proof/index.md) is valid and checks if it's currently included in the blockchain.



#### Return  


a [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) that returns true if valid, false if not



## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/verifyProof/#com.bloock.sdk.proof.entity.Proof#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a>proof| <a name="com.bloock.sdk/BloockClient/verifyProof/#com.bloock.sdk.proof.entity.Proof#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a><br><br>to validate<br><br>
  


#### Throws  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/verifyProof/#com.bloock.sdk.proof.entity.Proof#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a>Web3Exception| <a name="com.bloock.sdk/BloockClient/verifyProof/#com.bloock.sdk.proof.entity.Proof#com.bloock.sdk.config.entity.Network/PointingToDeclaration/"></a><br><br>Error connecting to blockchain.<br><br>
  


[jvm]  
Content  
fun [verifyProof](verify-proof.md)(proof: [Proof](../../com.bloock.sdk.proof.entity/-proof/index.md)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  



