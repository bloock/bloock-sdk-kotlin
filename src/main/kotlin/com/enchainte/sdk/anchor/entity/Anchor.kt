package com.enchainte.sdk.anchor.entity

data class Anchor(
    val id: Int = 0,
    val blockRoots: List<String> = emptyList(),
    val networks: List<Network> = emptyList(),
    val root: String = "",
    val status: String = ""
) {
    constructor(
        id: Int? = null,
        blockRoots: List<String>? = null,
        networks: List<Network>? = null,
        root: String? = null,
        status: String? = null
    ): this(id ?: 0, blockRoots ?: emptyList(), networks ?: emptyList(), root ?: "", status ?: "Pending")
}