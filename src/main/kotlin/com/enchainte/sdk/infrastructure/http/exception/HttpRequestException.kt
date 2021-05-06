package com.enchainte.sdk.infrastructure.http.exception

class HttpRequestException internal constructor(message: String?) :
    Exception("HttpClient response was not successful: ${message ?: "unknown error"}.")
