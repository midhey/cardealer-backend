package ru.ladamarket.models.user

import kotlinx.serialization.Serializable


@Serializable
class UserDTO(
    val surname: String,
    val name: String,
    val patronymic: String,
    val phone: String,
    val email: String,
    val hash: String,
    val salt: String,
    val avatar: String,
    val role: String
)
data class UserModel(
    val surname: String,
    val name: String,
    val patronymic: String,
    val phone: String,
    val email: String,
    val hash: String,
    val salt: String,
    val avatar: String,
    val role: String
)

fun UserDTO.toUserModel(): UserModel =
    UserModel(
        surname = surname,
        name = name,
        patronymic = patronymic,
        phone = phone,
        email = email,
        hash = hash,
        salt = salt,
        avatar = avatar,
        role = role
    )