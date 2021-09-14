# Bloock SDK - Kotlin / Java

This SDK offers all the features available in the Bloock Toolset:

- Write records
- Get records proof
- Validate proof
- Get records details

## Installation

### Requirements

- Java 1.8 or later

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation 'com.bloock.sdk:bloock-sdk:1.3.1'
```

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>com.bloock.sdk</groupId>
    <artifactId>bloock-sdk</artifactId>
    <version>1.3.1</version>
</dependency>
```

## Usage

The following examples summarize how to access the different functionalities available:

### Prepare data

In order to interact with the SDK, the data should be processed through the Record module.

There are several ways to generate a Record:

```kotlin
import com.bloock.sdk.record.entity.Record

// From a hash string (hex encoded 64-chars long string)
val record = Record.fromHash("5ac706bdef87529b22c08646b74cb98baf310a46bd21ee420814b04c71fa42b1")

// From a hex encoded string
val record = Record.fromHex("123456789abcdef")

// From a string
val record = Record.fromString("Example Data")
```

### Send records

This example shows how to send data to Bloock

```kotlin
import com.bloock.sdk.BloockClient
import com.bloock.sdk.record.entity.Record

val apiKey = System.getenv("API_KEY")!!

val client = BloockClient(apiKey)

val records = listOf(
    Record.fromString("Example Data")
)

val receipts = client.sendRecords(records).blockingGet()
```

### Get records status

This example shows how to get all the details and status of records:

```kotlin
import com.bloock.sdk.BloockClient
import com.bloock.sdk.record.entity.Record

val apiKey = System.getenv("API_KEY")!!

val client = BloockClient(apiKey)

val records = listOf(
    Record.fromString("Example Data 1"),
    Record.fromString("Example Data 2"),
    Record.fromString("Example Data 3")
)

val sendReceipt = client.sendRecords(records).blockingGet().first()

client.waitAnchor(sendReceipt.anchor).blockingSubscribe()

val recordReceipts = client.getRecords(records).blockingGet()
```

### Wait for records to process

This example shows how to wait for a record to be processed by Bloock after sending it.

```kotlin
import com.bloock.sdk.BloockClient
import com.bloock.sdk.record.entity.Record

val apiKey = System.getenv("API_KEY")!!

val client = BloockClient(apiKey)

val records = listOf(
    Record.fromString("Example Data 1"),
    Record.fromString("Example Data 2"),
    Record.fromString("Example Data 3")
)

val sendReceipt = client.sendRecords(records).blockingGet().first()

val receipt = client.waitAnchor(sendReceipt.anchor).blockingGet()
```

### Get and validate records proof

This example shows how to get a proof for an array of records and validate it:

```kotlin
import com.bloock.sdk.BloockClient
import com.bloock.sdk.record.entity.Record

val apiKey = System.getenv("API_KEY")!!

val client = BloockClient(apiKey)

val record = Record.fromString('Example Data 1')

val records = listOf(
    Record.fromString('Example Data 1'),
    Record.fromString('Example Data 2'),
    Record.fromString('Example Data 3')
)

val proof = client.getProof(records).blockingGet()
val timestamp = client.verifyProof(proof).blockingGet()
if (timestamp > 0) {
    println("Record is valid")
}
```

### Full example

This snippet shows a complete data cycle including: write, record status polling and proof retrieval and validation.

```kotlin
import com.bloock.sdk.BloockClient
import com.bloock.sdk.record.entity.Record

val charPool: List<Char> = ('a'..'f') + ('0'..'9')
val randomHex = (1..16)
    .map { kotlin.random.Random.nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")

val apiKey: String = System.getenv("API_KEY")
val client = BloockClient(apiKey)

val records = listOf(
    Record.fromString(randomHex)
)

println("SENDING MESSAGE: ${records[0].getHash()}")
val receipts = client.sendRecords(records).blockingGet()
assertNotNull(receipts)

println("WAITING MESSAGE")
client.waitAnchor(receipts[0].anchor).blockingGet()

println("VALIDATING MESSAGE")
val proof = client.getProof(records).blockingGet()
val timestamp = client.verifyProof(proof)

assertTrue(timestamp > 0)
```