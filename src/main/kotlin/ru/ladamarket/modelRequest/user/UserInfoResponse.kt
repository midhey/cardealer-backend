package ru.ladamarket.modelRequest.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse (
    val surname: String,
    val name: String,
    val patronymic: String,
    val phone: String,
    val email: String,
    val avatar: String
)


