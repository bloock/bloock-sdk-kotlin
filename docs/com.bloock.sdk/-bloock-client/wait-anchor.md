//[docs](../../index.md)/[com.bloock.sdk](../index.md)/[BloockClient](index.md)/[waitAnchor](wait-anchor.md)



# waitAnchor  
[jvm]  
Content  
@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)()  
  
fun [waitAnchor](wait-anchor.md)(anchor: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), timeout: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 120000): Single<[Anchor](../../com.bloock.sdk.anchor.entity/-anchor/index.md)>  
More info  


Waits until the anchor specified is confirmed in Bloock



#### Return  


a Single that will return a [Anchor](../../com.bloock.sdk.anchor.entity/-anchor/index.md)



## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a>anchor| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a><br><br>ID to wait for<br><br>
| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a>timeout| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a><br><br>time in miliseconds. After exceeding this time returns an exception. Default = 120000<br><br>
  


#### Throws  
  
|  Name|  Summary| 
|---|---|
| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a>InvalidArgumentException| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a><br><br>Informs that the input is not a number.<br><br>
| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a>AnchorNotFoundException| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a><br><br>The anchor provided could not be found.<br><br>
| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a>WaitAnchorTimeoutException| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a><br><br>Returned when the function has exceeded the timeout.<br><br>
| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a>HttpRequestException| <a name="com.bloock.sdk/BloockClient/waitAnchor/#kotlin.Int#kotlin.Int/PointingToDeclaration/"></a><br><br>Error return by Bloock's API.<br><br>
  



