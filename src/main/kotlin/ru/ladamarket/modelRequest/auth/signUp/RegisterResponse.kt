package ru.ladamarket.modelRequest.auth.signUp

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val successful: Boolean,
    val message: String
)