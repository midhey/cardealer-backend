package ru.ladamarket.modelRequest.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoUpdate(
    val email: String,
    val phone: String,
    val hash: String,
    val avatar: String
)
