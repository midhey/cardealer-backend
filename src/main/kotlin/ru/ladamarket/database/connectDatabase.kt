package ru.ladamarket.database

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database

fun Application.connectDataBaseRouting(){
    routing {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/cardealership",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "Brough-90"
        )
    }
}