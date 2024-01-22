package ru.ladamarket.plugins


import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.ladamarket.database.services.carServices.body.BodyService
import ru.ladamarket.database.services.carServices.carModel.CarModelService
import ru.ladamarket.database.services.carServices.colorToModel.ColorToModelService
import ru.ladamarket.database.services.carServices.engine.EngineService
import ru.ladamarket.database.services.carServices.equipment.EquipmentService
import ru.ladamarket.database.services.carServices.transmission.TransmissionService
import ru.ladamarket.database.services.color.ColorService
import ru.ladamarket.database.services.news.NewsService
import ru.ladamarket.database.services.order.OrderService
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.routes.admin.admin
import ru.ladamarket.routes.auth.auth
import ru.ladamarket.routes.auth.userGroup
import ru.ladamarket.routes.car.body.body
import ru.ladamarket.routes.car.carModel.carModel
import ru.ladamarket.routes.car.colorToModel.colorToModel
import ru.ladamarket.routes.car.engine.engine
import ru.ladamarket.routes.car.equipment.equipment
import ru.ladamarket.routes.car.transmission.transmission
import ru.ladamarket.routes.color.color
import ru.ladamarket.routes.news.news
import ru.ladamarket.routes.order.order
import ru.ladamarket.routes.user.profile
import ru.ladamarket.security.hash.HashingService
import ru.ladamarket.security.token.TokenConfig
import ru.ladamarket.security.token.TokenService

fun Application.configureRouting(config: TokenConfig) {
    install(AutoHeadResponse)
    val userService: UserService by inject()
    val hashingService: HashingService by inject()
    val tokenService: TokenService by inject()
    val colorService: ColorService by inject()
    val engineService: EngineService by inject()
    val carModelService: CarModelService by inject()
    val transmissionService: TransmissionService by inject()
    val bodyService: BodyService by inject()
    val equipmentService: EquipmentService by inject()
    val colorToModelService: ColorToModelService by inject()
    val orderService: OrderService by inject()
    val newsService: NewsService by inject()

    routing {
        auth(userService, tokenService, hashingService, config)
        profile(userService, hashingService)
        userGroup(userService)
        admin(userService)
        color(colorService, userService)
        engine(engineService)
        carModel(carModelService, equipmentService)
        transmission(transmissionService)
        body(bodyService)
        equipment(
            carModelService,
            bodyService,
            transmissionService,
            engineService,
            equipmentService
        )
        colorToModel(
            colorToModelService,
            colorService
        )
        order(
            orderService,
            userService,
            carModelService,
            bodyService,
            transmissionService,
            engineService,
            equipmentService,
            colorToModelService,
            colorService
        )
        news(newsService, userService)
    }
}

//fun Application.configureRouting(
//    val userService: SHA256HashingService by inject()
//    val hashingService: HashingService by inject()
//    val tokenService: TokenService by inject()
//) {
//    install(AutoHeadResponse)
//        routing {
//            auth(
//                userService = userService,
//                tokenService = tokenService,
//                hashingService = hashingService,
//                tokenConfig = config
//            )
////        signUp(hashingService)
////        signIn(hashingService, tokenService, config)
////        profile()
//        }
//}
