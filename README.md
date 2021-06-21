# Enchainté SDK - Kotlin / Java

This SDK offers all the features available in the Enchainté Toolset:

- Write messages
- Get messages proof
- Validate proof
- Get messages details

## Installation

### Requirements

- Java 1.8 or later

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation 'com.enchainte.sdk:enchainte-sdk:1.1.0'
```

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>com.enchainte.sdk</groupId>
    <artifactId>enchainte-sdk</artifactId>
    <version>1.1.0</version>
</dependency>
```

## Usage

The following examples summarize how to access the different functionalities available:

### Prepare data

In order to interact with the SDK, the data should be processed through the Message module.

There are several ways to generate a Message:

```kotlin
import com.enchainte.sdk.message.entity.Message

// From a hash string (hex encoded 64-chars long string)
val message = Message.fromHash("5ac706bdef87529b22c08646b74cb98baf310a46bd21ee420814b04c71fa42b1")

// From a hex encoded string
val message = Message.fromHex("123456789abcdef")

// From a string
val message = Message.fromString("Example Data")
```

### Send messages

This example shows how to send data to Enchainté

```kotlin
import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.message.entity.Message

val apiKey = System.getenv("API_KEY")!!

val client = EnchainteClient(apiKey)

val messages = listOf(
    Message.fromString("Example Data")
)

val receipts = client.sendMessages(messages).blockingGet()
```

### Get messages status

This example shows how to get all the details and status of messages:

```kotlin
import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.message.entity.Message

val apiKey = System.getenv("API_KEY")!!

val client = EnchainteClient(apiKey)

val messages = listOf(
    Message.fromString("Example Data 1"),
    Message.fromString("Example Data 2"),
    Message.fromString("Example Data 3")
)

val sendReceipt = client.sendMessages(messages).blockingGet().first()

client.waitAnchor(sendReceipt.anchor).blockingSubscribe()

val messageReceipts = client.getMessages(messages).blockingGet()
```

### Wait for messages to process

This example shows how to wait for a message to be processed by Enchainté after sending it.

```kotlin
import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.message.entity.Message

val apiKey = System.getenv("API_KEY")!!

val client = EnchainteClient(apiKey)

val messages = listOf(
    Message.fromString("Example Data 1"),
    Message.fromString("Example Data 2"),
    Message.fromString("Example Data 3")
)

val sendReceipt = client.sendMessages(messages).blockingGet().first()

val receipt = client.waitAnchor(sendReceipt.anchor).blockingGet()
```

### Get and validate messages proof

This example shows how to get a proof for an array of messages and validate it:

```kotlin
import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.message.entity.Message

val apiKey = System.getenv("API_KEY")!!

val client = EnchainteClient(apiKey)

val message = Message.fromString('Example Data 1')

val messages = listOf(
    Message.fromString('Example Data 1'),
    Message.fromString('Example Data 2'),
    Message.fromString('Example Data 3')
)

val proof = client.getProof(messages).blockingGet()
val valid = client.verifyProof(proof).blockingGet()
```

### Full example

This snippet shows a complete data cycle including: write, message status polling and proof retrieval and validation.

```kotlin
import com.enchainte.sdk.EnchainteClient
import com.enchainte.sdk.message.entity.Message

val charPool: List<Char> = ('a'..'f') + ('0'..'9')
val randomHex = (1..16)
    .map { kotlin.random.Random.nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")

val apiKey: String = System.getenv("API_KEY")
val client = EnchainteClient(apiKey)

val messages = listOf(
    Message.fromString(randomHex)
)

println("SENDING MESSAGE: ${messages[0].getHash()}")
val receipts = client.sendMessages(messages).blockingGet()
assertNotNull(receipts)

println("WAITING MESSAGE")
client.waitAnchor(receipts[0].anchor).blockingGet()

println("VALIDATING MESSAGE")
val proof = client.getProof(messages).blockingGet()
val timestamp = client.verifyProof(proof)

assertTrue(timestamp > 0)
```