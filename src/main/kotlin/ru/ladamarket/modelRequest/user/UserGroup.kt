package ru.ladamarket.modelRequest.user

import kotlinx.serialization.Serializable

@Serializable
data class UserGroup(
    val role: String
)
