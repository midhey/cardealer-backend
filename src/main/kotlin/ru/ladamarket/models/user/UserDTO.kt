package ru.ladamarket.models.user

import kotlinx.serialization.Serializable


@Serializable
class UserDTO(
    val surname: String,
    val name: String,
    val patronymic: String,
    val phone: String,
    val email: String,
    val avatar: String,
)
