package ru.ladamarket.models.user

data class User(
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