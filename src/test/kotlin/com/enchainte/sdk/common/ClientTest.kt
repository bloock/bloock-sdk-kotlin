package com.enchainte.sdk.common

import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(ClientResolver::class)
annotation class ClientTest(
)