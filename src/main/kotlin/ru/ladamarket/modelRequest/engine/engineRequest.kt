package ru.ladamarket.modelRequest.engine

import kotlinx.serialization.Serializable

@Serializable
data class engineRequest(
    val engineId: Short
)
