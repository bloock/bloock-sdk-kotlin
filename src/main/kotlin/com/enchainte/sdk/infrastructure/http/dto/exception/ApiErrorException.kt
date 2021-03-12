package com.enchainte.sdk.infrastructure.http.dto.exception

import com.enchainte.sdk.infrastructure.http.dto.ApiError

class ApiErrorException internal constructor(error: ApiError?) :
    Exception("API request returned an error: ${error?.message} (${error?.status})")
