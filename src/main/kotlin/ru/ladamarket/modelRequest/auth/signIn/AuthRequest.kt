package ru.ladamarket.modelRequest.auth.signIn

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val email: String,
    val phone: String,
    val hash: String
)