package com.enchainte.sdk.anchor.entity

data class Anchor(
    val id: Int,
    val blockRoots: List<String>,
    val networks: List<Network>,
    val root: String,
    val status: String
)