package ru.ladamarket.di

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.ladamarket.database.DataBaseConfig
import ru.ladamarket.database.services.carModel.CarModelService
import ru.ladamarket.database.services.carModel.CarModelServiceImpl
import ru.ladamarket.database.services.color.ColorService
import ru.ladamarket.database.services.color.ColorServiceImpl
import ru.ladamarket.database.services.engine.EngineService
import ru.ladamarket.database.services.engine.EngineServiceImpl
import ru.ladamarket.database.services.transmission.TransmissionService
import ru.ladamarket.database.services.transmission.TransmissionServiceImpl
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.database.services.user.UserServiceImpl
import ru.ladamarket.security.hash.HashingService
import ru.ladamarket.security.hash.SHA256HashingService
import ru.ladamarket.security.token.JwtTokenService
import ru.ladamarket.security.token.TokenService

val config = HoconApplicationConfig(ConfigFactory.load())
val dbConfig = config.config("database")

val databaseModule = module {

    single {

        val dataBaseConfig = DataBaseConfig(
            user = dbConfig.property("user").getString(),
            password = dbConfig.property("password").getString(),
            database = dbConfig.property("name").getString(),
            ip = dbConfig.property("address").getString()+":"+dbConfig.property("port").getString()
        )
        Database.connect(
            url = "jdbc:postgresql://${dataBaseConfig.ip}/${dataBaseConfig.database}",
            driver = "org.postgresql.Driver",
            user = dataBaseConfig.user,
            password = dataBaseConfig.password
        )
    }
    single<UserService> { UserServiceImpl(get()) }
    single<ColorService> { ColorServiceImpl(get()) }
    single<EngineService> { EngineServiceImpl(get()) }
    single<TransmissionService> { TransmissionServiceImpl(get()) }
    single<CarModelService> { CarModelServiceImpl(get())}
}

val securityModule = module {
    factoryOf(::SHA256HashingService) { bind<HashingService>() }
    factoryOf(::JwtTokenService) { bind<TokenService>() }
}