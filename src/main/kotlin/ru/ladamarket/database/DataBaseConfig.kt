package ru.ladamarket.database

data class DataBaseConfig(
    val user: String,
    val password: String,
    val database: String,
    val ip: String
)