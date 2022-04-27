package com.bloock.sdk.shared

data class Signature(
    val signature: String,
    val header: Headers
)

data class Headers(
    val kty: String?,
    val crv: String?,
    val alg: String?,
    val kid: String?,
    val other: Map<String, Any>
) {
    constructor() : this(null,null,null,null, emptyMap())
}