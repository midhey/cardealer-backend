package ru.ladamarket.database

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database

fun Application.connectDataBase(){
    val address = environment.config.property("database.address").getString()
    val port = environment.config.property("database.port").getString()
    val name = environment.config.property("database.name").getString()
    val password = environment.config.property("database.password").getString()
    routing {
        Database.connect(
            url = "jdbc:postgresql://$address:$port/$name",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "$password"
        )
    }
}