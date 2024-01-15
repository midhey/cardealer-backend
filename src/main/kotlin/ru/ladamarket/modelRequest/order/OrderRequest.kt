package ru.ladamarket.modelRequest.order

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class OrderRequest(
    val equipmentId: Int,
    val colorId: Int
)
