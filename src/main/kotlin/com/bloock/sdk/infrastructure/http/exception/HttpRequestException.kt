package com.bloock.sdk.infrastructure.http.exception

class HttpRequestException internal constructor(record: String?) :
    Exception("HttpClient response was not successful: ${record ?: "unknown error"}.")
