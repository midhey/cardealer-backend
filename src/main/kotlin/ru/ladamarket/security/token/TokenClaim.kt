package ru.ladamarket.security.token

data class TokenClaim(
    val name: String,
    val value: Int?
)