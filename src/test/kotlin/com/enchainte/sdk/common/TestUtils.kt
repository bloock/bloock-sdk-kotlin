package com.enchainte.sdk.common

import io.ktor.http.*

internal val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
internal val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"