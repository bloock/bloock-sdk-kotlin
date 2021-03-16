package com.enchainte.sdk.anchor.entity.dto

import com.enchainte.sdk.anchor.entity.Network
import com.google.gson.annotations.SerializedName

internal data class AnchorRetrieveResponse(
    @SerializedName("anchor_id")
    val id: Int?,
    @SerializedName("block_roots")
    val blockRoots: List<String>?,
    val networks: List<Network>?,
    val root: String?,
    val status: String?
)