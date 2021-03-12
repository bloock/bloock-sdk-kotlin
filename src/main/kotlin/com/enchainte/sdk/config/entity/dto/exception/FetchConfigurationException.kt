package com.enchainte.sdk.config.entity.dto.exception

class FetchConfigurationException internal constructor(error: String) : Exception("Couldn't fetch from Azure: $error")
