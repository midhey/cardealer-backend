package ru.ladamarket.modelRequest.auth.signUp

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val surname:String,
    val name: String,
    val patronymic: String,
    val phone: String,
    val email: String,
    val hash: String,
    val avatar: String,
    val role: String
)