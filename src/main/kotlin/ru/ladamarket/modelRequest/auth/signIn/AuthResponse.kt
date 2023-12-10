package ru.ladamarket.modelRequest.auth.signIn

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String ?= "",
    val message: String
)