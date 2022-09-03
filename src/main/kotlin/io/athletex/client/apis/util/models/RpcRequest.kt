package io.athletex.client.apis.util.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RpcRequest(
    @SerialName("id")
    val id: Int,
    @SerialName("jsonrpc")
    val jsonRpc: String,
    @SerialName("method")
    val method: String,
    @SerialName("params")
    val params: List<Param>
)

@Serializable
data class Param(
    @SerialName("to")
    val to: String,
    @SerialName("data")
    val data: String
)