package ru.ladamarket.modelRequest.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoUpdate(
    val surname: String?,
    val name: String?,
    val patronymic: String?,
    val email: String?,
    val phone: String?,
    val hash: String?,
    val avatar: String?
)
