package io.athletex.client.apis.util.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RpcResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("jsonrpc")
    val jsonrpc: String,
    @SerialName("result")
    val result: String
)